package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;


@Controller
@Slf4j
public class Handler {

    @PostMapping("")
    public ResponseEntity<String> priceRetrieval() {

        System.out.println("service was asked");

        CompletableFuture<String> hiWell = CompletableFuture.supplyAsync(() ->
                Chrome.runWithChrome("hiwell", WebsiteRetrieval::retrieveDataFromHiWell));

        CompletableFuture<String> psikolojiMerkezi = CompletableFuture.supplyAsync(() ->
                Chrome.runWithChrome("psikoloji merkezi", WebsiteRetrieval::retrieveDataFromPsikolojiMerkezi));

        CompletableFuture<String> noraPsikoloji = CompletableFuture.supplyAsync(() ->
                Chrome.runWithChrome("nora psikoloji", WebsiteRetrieval::retireveDataFromNoraPsikologi));

        CompletableFuture<String> evidemkiPsikoloji = CompletableFuture.supplyAsync(() ->
                Chrome.runWithChrome("evidemki psikoloji", WebsiteRetrieval::retrieveDataFromEvidemkiPsikoloji));

        CompletableFuture.allOf(hiWell, noraPsikoloji, psikolojiMerkezi)
                .thenApplyAsync(ignoredResult -> {
                    log.info("all tasks completed");
                    StringBuilder priceChangeMessage = new StringBuilder();
                    String alertColor = "good";

                    try {
                        for (String company : Constants.lastPrice.keySet()) {
                            if (!Constants.currentPrice.get(company).equals(Constants.lastPrice.get(company))) {
                                if (priceChangeMessage.length() > 0) {
                                    priceChangeMessage.append("\n");
                                }
                                priceChangeMessage.append("Price for ")
                                        .append(company).append(" was ")
                                        .append(Constants.lastPrice.get(company))
                                        .append(" on the ")
                                        .append(Constants.dateOfLastPrice.get(company));
                            }
                            Constants.lastPrice.put(company, Constants.currentPrice.get(company));
                            Constants.dateOfLastPrice.put(company, new SimpleDateFormat("dd/MM/yy").format(new Date()));
                        }

                        if (priceChangeMessage.length() == 0) {
                            priceChangeMessage = new StringBuilder("no changes in prices since last check");
                        } else { // changes were detected, so the change message should appear red
                            alertColor = "attention";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


        try {
            HttpRequest postRequest;
            postRequest = HttpRequest.newBuilder()
                    .uri(new URI(Constants.incomingWebhookURI))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(Constants.json.
                            formatted(new SimpleDateFormat("dd/MM/yy").format(new Date()), hiWell.get(),
                                    noraPsikoloji.get(), psikolojiMerkezi.get(), priceChangeMessage.toString(), alertColor)))
                    .build();
            log.info("sent post request to microsoft webhook");
            HttpClient httpClient = HttpClient.newHttpClient();
            httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         return null;
         });

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{ \"text\": \"Thank you for your request. I will respond asap\" }");
    }
}
