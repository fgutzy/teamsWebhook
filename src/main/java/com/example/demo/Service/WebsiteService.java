package com.example.demo.Service;

import com.example.demo.Chrome;
import com.example.demo.Constants;
import com.example.demo.Entity.Company;
import com.example.demo.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WebsiteService {

    static String currentDate = new SimpleDateFormat("dd/MM/yy").format(new Date());


    @Autowired
    CompanyRepository companyRepository;

    public void updateValuesOfCompany(String company, int minPrice, int maxPrice, String date) {
        if (maxPrice == 0) { // case that company has no range in prices
            companyRepository.save(new Company(company, minPrice, date));
        } else {
            System.out.println("saved weekly run results for " + company);
            companyRepository.save(new Company(company, minPrice, maxPrice, date));
        }
    }

    public String retrieveDataFromMantraCare(Chrome chrome) {
        String returner = "";
        log.info("accessed url of mantracare");
        chrome.driver.get("https://programs.mantracare.org/plans/");
        log.info("clicked on \"select country\"");
        chrome.driver.findElement(By.id("input_230_3_chosen")).click();
        log.info("selected turkey");
        chrome.driver.findElement(By.xpath("//*[@id=\"input_230_3_chosen\"]/div/ul/li[141]")).click();
        try {
            Thread.sleep(3000);
            log.info(" clicked on continue");
            chrome.driver.findElement(By.id("gform_next_button_230_1")).click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(" clicked on trial session");
        chrome.driver.findElement(By.id("label_230_50_0")).click();
        int price = Integer.parseInt(chrome.driver.findElement(By.xpath("//*[@id=\"field_230_49\"]/div/p[1]/span[2]")).getText());
        returner += price + " TL" + " for ";
        log.info("retrieved price of " + returner);
        returner += chrome.driver.findElement(By.xpath("//*[@id=\"field_230_49\"]/div/ul/li[2]")).getText().substring(2);
        log.info("Complete retrieval from matracare " + returner);
        updateValuesOfCompany("mantra care", price, 0, currentDate);
        return returner;
    }

    public String retrieveDataFromHiWell(Chrome chrome) {
        String returner = "";
        log.info("declared and initliazed driver for hiwell");
        log.info("accessed url of hiwell");
        chrome.driver.get("https://portal.hiwellapp.com/sign-in");
        log.info("entered email for hiwell");
        WebElement emailInput = new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.
                presenceOfElementLocated(By.id("email")));
        emailInput.sendKeys(Constants.hiWellEmail);
        log.info("entered password for hiwell");
        chrome.driver.findElement(By.id("password")).sendKeys(Constants.hiWellPassword);
        log.info("clicked login for hiwell");
        chrome.driver.findElement(By.xpath("/html/body/app-root/layout/empty-layout/div/div/auth-sign-in/div/div[1]/div/div[2]/form/button")).click();

        log.info("clicked purchase for hiwell");
        WebElement purchaseButton = new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/panel/app-client-home/div/div[1]/div[2]/div[1]/div[1]/div[3]/button/span[1]")));
        purchaseButton.click();

        log.info("retrieved and returned price for hiwell");
        WebElement priceElement = new WebDriverWait(chrome.driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/app-payment-plans/div/div[2]/div[1]/div/div[1]/div/div/span[3]")));
        int price = Integer.parseInt(priceElement.getText().substring(1));
        returner += price + " TL";
        log.info(returner);
        returner += " for " + chrome.driver.findElement(By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/app-payment-plans/div/div[2]/div[1]/div/div[1]/div/ul/li[1]/span")).getText().substring(0, 6);
        log.info("Complete retrieval from hiwell " + returner);
        updateValuesOfCompany("hiwell", price, 0, currentDate);
        return returner;
    }


    public String retireveDataFromNoraPsikologi(Chrome chrome) {
        String returner = "";
        log.info("declared and initliazed driver for nora psikoloji");
        log.info("acessed url of nora psikoloji");
        chrome.driver.get("https://norapsikoloji.com/online-randevu-3/");
        log.info("retrieved price");
        String priceField = new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.className("booknetic_service_card_price"))).getText();
        int price = Integer.parseInt(priceField.substring(0, 5).replaceAll("[^\\d]", ""));
        returner += priceField;
        log.info("retrieved duration for nora psikoloji");
        returner += " for " + chrome.driver.findElement(By.className("booknetic_service_duration_span")).getText();
        log.info("Complete retrieval from nora psikoloji " + returner);
        updateValuesOfCompany("nora psikoloji", price, 0, currentDate);
        return returner;
    }

    public String retrieveDataFromPsikolojiMerkezi(Chrome chrome) {
        String returner = "";
        log.info("declared and initliazed driver for psikoloji merkezi");
        log.info("accessed url of onlinepsikolojimerkezi with maximum price set to 9000");
        chrome.driver.get("https://www.onlinepsikolojimerkezi.com/searchconsultant?_token=0xlWfFynpNykHF1AFu1EXcoZuEujUbuRs8nZOPXJ&nm=&sp=&lp=&hp=9000");
        log.info("retrieved price of first (cheapest) psychologist for psikoloji merkezi");
        //returner += chrome.driver.findElement(By.xpath("/html/body/div[1]/section/div/div[3]/div/div/div[1]/div[2]/div[1]/p/b")).getText();
        int priceMin = Integer.parseInt(chrome.driver.findElement(By.xpath("/html/body/div[1]/section/div/div[3]/div/div/div[1]/div[2]/div[1]/p/b")).getText().substring(0, 3));
        returner += priceMin + " TL - ";
        List<WebElement> allCards = chrome.driver.findElements(By.className("card"));
        log.info("retrieved price of last (priciest) psychologist for psikoloji merkezi");
        int priceMax = Integer.parseInt(allCards.get(allCards.size() - 1).findElement(By.tagName("b")).getText().substring(0, 4));
        returner += " for " + allCards.get(allCards.size() - 1).findElement(By.className("clini-infos")).getText().substring(28, 37);
        log.info("Complete retrieval from psikoloji merkezi " + returner);
        updateValuesOfCompany("psikoloji merkezi", priceMin, priceMax, currentDate);
        return returner;
    }

    public String retrieveDataFromTerappin(Chrome chrome) {
        String returner = "";
        log.info("entered url of terappin");
        chrome.driver.get("https://app.terappin.com/login");
        log.info("entered email for terrapin");
        chrome.driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[2]/div/div[1]/div/input")).sendKeys("javaserviceferdi@gmail.com");
        log.info("entered password for terrapin");
        chrome.driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[2]/div/div[2]/div/input")).sendKeys("Ferdi.99");
        log.info("clicked login for terrapin");
        chrome.driver.findElement(By.className("register-button")).click();
        log.info("clicked confirm button");
        new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.className("swal-button--confirm"))).click();

        log.info("clicked select psychologist");
        chrome.driver.get("https://app.terappin.com/recommendation-therapist/2/ok/tr/1/1200");
        List<WebElement> therapists = new WebDriverWait(chrome.driver, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("detail")));
        List<Double> prices = new ArrayList<>();

        log.info("added prices to list");
        for (WebElement element : therapists) {
            prices.add(Double.valueOf(element.findElement(By.tagName("h3")).getText().substring(0, 3)));
        }

        log.info("sorted list");
        Collections.sort(prices);

        String durationOfSession = therapists.get(0).findElement(By.tagName("h2")).getText().substring(0, 9);
        int priceMin = prices.get(0).intValue();
        int priceMax = prices.get(prices.size() - 1).intValue();
        returner += priceMin + " - " + priceMax + "TL for " + durationOfSession;
        log.info("Complete retrieval from Terrapin " + returner);
        updateValuesOfCompany("terappin", priceMin, priceMax, currentDate);
        return returner;
    }

    public String retrieveDataFromPsikologOfisi(Chrome chrome) {
        String returner = "";

        log.info("entered url of psikolog ofisi");
        chrome.driver.get("https://www.psikologofisi.com/fiyatlar");

        log.info("retrieved all price fields");
        List<WebElement> priceFields = chrome.driver.findElements(By.cssSelector("div[class=\"col-md-4 col-sm-5\""));
        String durationOfSession = priceFields.get(0).getText().substring(24, 33);
        List<Integer> prices = new ArrayList<>();


        log.info("retrieved prices of the fields");
        for (WebElement price : priceFields) {
            prices.add(Integer.valueOf(price.getText().substring(17, 20)));
        }

        log.info("sorted price-list");
        Collections.sort(prices);

        int priceMin = prices.get(0);
        int priceMax = prices.get(prices.size() - 1);
        returner += priceMin + " - " + priceMax + "TL for " + durationOfSession;
        log.info("Complete retrieval from psikolog ofisi " + returner);
        updateValuesOfCompany("psikolog ofisi", priceMin, priceMax, currentDate);
        return returner;
    }
}
