package com.example.demo;

import com.example.demo.Entity.Company;
import com.example.demo.Service.WebsiteService;
import com.example.demo.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Component
@Controller
@SuppressWarnings("unused")
public class RequestHandler {

    @Autowired
    CompanyRepository companyPostRepository;

    @Autowired
    WebsiteService websiteService;

    Company hiwell = new Company();
    Company mantraCare = new Company();
    Company noraPsikoloji = new Company();
    Company psikolojiMerkezi = new Company();
    Company terappin = new Company();
    Company psikologOfisi = new Company();

    String alertColor = "good";

    StringBuilder priceChangeMessage;

    @PostMapping("/apiCall")
    public ResponseEntity<String> apiCall() {
        CompletableFuture.runAsync(this::retrieval);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{ \"text\": \"Thank you for your request. I will respond asap\" }");
    }

    //every thursday at 5am heroku time (UTC) execute
    @Scheduled(cron = "0 0 5 * * THU")
    public void executeWeeklyMethod() {
        retrieval();
    }

    private void retrieval() {
        priceChangeMessage = new StringBuilder();
        log.info("start");

        try (Chrome chrome = new Chrome(companyPostRepository)) {
            log.info("inside try");

            try {
                hiwell.setCurrentPrice(websiteService.retrieveDataFromHiWell(chrome));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                mantraCare.setCurrentPrice(websiteService.retrieveDataFromMantraCare(chrome));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                noraPsikoloji.setCurrentPrice(websiteService.retireveDataFromNoraPsikologi(chrome));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                psikolojiMerkezi.setCurrentPrice(websiteService.retrieveDataFromPsikolojiMerkezi(chrome));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                psikologOfisi.setCurrentPrice(websiteService.retrieveDataFromPsikologOfisi(chrome));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                terappin.setCurrentPrice(websiteService.retrieveDataFromTerappin(chrome));
            } catch (Exception e) {
                e.printStackTrace();
            }

            log.info("all tasks completed");

            List<Company> companiesContainingLastPrice = (List<Company>) companyPostRepository.findAll();
            List<String> companiesContainingCurrentPrice = new ArrayList<>(Arrays.asList(hiwell.getCurrentPrice(),
                    noraPsikoloji.getCurrentPrice(), psikolojiMerkezi.getCurrentPrice(), mantraCare.getCurrentPrice(),
                    terappin.getCurrentPrice(), psikologOfisi.getCurrentPrice()));

            for (int i = 0; i < companiesContainingLastPrice.size(); i++) {

                try {
                    Company currentCompany = companiesContainingLastPrice.get(i);
                    log.info("Current company: " + currentCompany.getName());

                    String currentPrice = companiesContainingCurrentPrice.get(i);
                    log.info("Current price of " + currentCompany.getName() + " is: " + currentPrice);

                    if (!currentCompany.getLastPrice().equals(currentPrice)) {
                        log.info("Price changes detected for " + currentCompany.getName() + " last price: " + currentCompany.getLastPrice()
                                + " current price: " + currentPrice);

                        if (priceChangeMessage.length() > 0) {
                            priceChangeMessage.append("\n");
                        }
                        priceChangeMessage.append("Price for ")
                                .append(currentCompany.getName()).append(" was ")
                                .append(currentCompany.getLastPrice())
                                .append(" on the ")
                                .append(currentCompany.getDateOfLastChange());
                        log.info("price for " + currentCompany.getName() + " was updated from " + currentCompany.getLastPrice() + " to " + currentPrice);
                        companyPostRepository.updateLastPriceByName(currentPrice, currentCompany.getName());
                        companyPostRepository.updateDateByName(new SimpleDateFormat("dd/MM/yy").format(new Date()), currentCompany.getName());
                        log.info("date for " + currentCompany.getName() + " was updated to " + currentCompany.getDateOfLastChange());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (priceChangeMessage.isEmpty()) {
                    priceChangeMessage = new StringBuilder("no changes in prices since last check");
                } else { // changes were detected, so the change message should appear red
                    alertColor = "attention";
                }
            }
            sendPostToWebhook();
        }
    }


    private void sendPostToWebhook() {
        try {
            log.info("send post request");
            HttpRequest postRequest;
            postRequest = HttpRequest.newBuilder()
                    .uri(new URI(Constants.incomingWebhookURI))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(Constants.json.
                            formatted(new SimpleDateFormat("dd/MM/yy").format(new Date()),
                                    hiwell.getCurrentPrice(), mantraCare.getCurrentPrice(), noraPsikoloji.getCurrentPrice(),
                                    psikolojiMerkezi.getCurrentPrice(), psikologOfisi.getCurrentPrice(), terappin.getCurrentPrice(),
                                    priceChangeMessage.toString(), alertColor)))

                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


