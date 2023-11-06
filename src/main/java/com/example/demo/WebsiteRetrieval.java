package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Slf4j
public class WebsiteRetrieval {

    protected static String retrieveDataFromHiWell(Chrome chrome, String nameOfCompany) {
        String returner = "";
        log.info("declared and initliazed driver for hiwell");
        log.info("accessed url of hiwell");
        chrome.driver.get("https://portal.hiwellapp.com/sign-in");
        log.info("entered email for hiwell");
        WebElement emailInput = new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.
                presenceOfElementLocated(By.id("email")));
        emailInput.sendKeys(Constants.hiWellEmail);
        //chrome.driver.findElement(By.id("email")).sendKeys(Constants.hiWellEmail);
        log.info("entered password for hiwell");
        chrome.driver.findElement(By.id("password")).sendKeys(Constants.hiWellPassword);
        log.info("clicked login for hiwell");
        chrome.driver.findElement(By.xpath("/html/body/app-root/layout/empty-layout/div/div/auth-sign-in/div/div[1]/div/div[2]/form/button")).click();

        log.info("clicked purchase for hiwell");
        WebElement purchaseButton = new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/panel/app-client-home/div/div[1]/div[2]/div[1]/div[1]/div[3]/button/span[1]")));
        purchaseButton.click();

        log.info("retrieved and returned price for hiwell");
        WebElement priceElement = new WebDriverWait(chrome.driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/app-payment-plans/div/div[2]/div[1]/div/div[1]/div/div/span[3]")));
                             /*      returner += new WebDriverWait(chrome.driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/app-payment-plans/div/div[2]/div[2]/div[1]/div/span[2]")))
                .getText().substring(1) + " TL";*/
        returner += priceElement.getText().substring(1) + " TL";
        log.info(returner);
        returner += " for " + chrome.driver.findElement(By.xpath("/html/body/app-root/layout/hiwell-client-portal-layout/div/div/app-payment-plans/div/div[2]/div[1]/div/div[1]/div/ul/li[1]/span")).getText().substring(0, 6);
        log.info("Complete retrieval from hiwell " + returner);
        return returner;
    }


    protected static String retireveDataFromNoraPsikologi(Chrome chrome, String nameOfCompany) {
        String returner = "";
        log.info("declared and initliazed driver for nora psikoloji");
        log.info("acessed url of nora psikoloji");
        chrome.driver.get("https://norapsikoloji.com/online-randevu-3/");
        log.info("retrieved price");
        String price = new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.className("booknetic_service_card_price"))).getText();
        returner += price;
        System.out.println(returner);
        log.info("retrieved duration for nora psikoloji");
        returner += " for " + chrome.driver.findElement(By.className("booknetic_service_duration_span")).getText();
        log.info("Complete retrieval from nora psikoloji " + returner);
        return returner;
    }

    protected static String retrieveDataFromPsikolojiMerkezi(Chrome chrome, String nameOfCompany) {
        String returner = "";
        log.info("declared and initliazed driver for psikoloji merkezi");
        log.info("accessed url of onlinepsikolojimerkezi with maximum price set to 9000");
        chrome.driver.get("https://www.onlinepsikolojimerkezi.com/searchconsultant?_token=0xlWfFynpNykHF1AFu1EXcoZuEujUbuRs8nZOPXJ&nm=&sp=&lp=&hp=9000");
        log.info("retrieved price of first (cheapest) psychologist for psikoloji merkezi");
        returner += chrome.driver.findElement(By.xpath("/html/body/div[1]/section/div/div[3]/div/div/div[1]/div[2]/div[1]/p/b")).getText();
        List<WebElement> allCard = chrome.driver.findElements(By.className("card"));
        log.info("retrieved price of last (priciest) psychologist for psikoloji merkezi");
        returner += " - " + allCard.get(allCard.size() - 1).findElement(By.tagName("b")).getText();
        returner += " for " + allCard.get(allCard.size() - 1).findElement(By.className("clini-infos")).getText().substring(28, 37);
        log.info("Complete retrieval from psikoloji merkezi " + returner);
        return returner;
    }

    protected static String retrieveDataFromEvidemkiPsikoloji(Chrome chrome, String nameOfCompany) {
        String returner = "";
        log.info("declared and initliazed driver for evidemki psikoloji");
        log.info("accessed url of evidemki psikoloji");
        chrome.driver.get("https://app.evimdekipsikolog.com");
        log.info("Accepted cookies");
        chrome.driver.findElement(By.id("CookieAccept")).click();
        log.info("clicked on price filter for evidemki psikoloji");

        Actions act = new Actions(chrome.driver);
        WebElement ele = chrome.driver.findElement(By.id("dropdownMenu1"));
        act.doubleClick(ele).perform();
        chrome.driver.findElement(By.id("dropdownMenu1")).click();

        log.info("filtered by cheapest first for evidemki psikoloji");
        chrome.driver.findElement(By.xpath("//*[@id=\"filter-mobile-footer\"]/div/div/ul/li[1]/button")).click();
        log.info("saved maximum price for evidemki psikoloji");
        returner += new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"home-consultant-container\"]/div[1]/div[1]/div[2]/div/div[2]/div/div[1]/div[2]/b"))).getText();

        log.info("clicked on price filter for evidemki psikoloji");
        chrome.driver.findElement(By.id("dropdownMenu1")).click();
        log.info("filtered by priciest first");
        chrome.driver.findElement(By.xpath("//*[@id=\"filter-mobile-footer\"]/div/div/ul/li[2]/button")).click();

        log.info("saved minimum price for evidemki psikoloji");

        WebElement mostExpensivePsychologistCard = chrome.driver.findElement(By.className("appointment-info-desktop"));
        returner += " - " + new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(
                By.tagName("b"))).getText();

        returner += " - " + new WebDriverWait(chrome.driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"home-consultant-container\"]/div[1]/div[1]/div[2]/div/div[2]/div/div[1]/div[2]/b"))).getText();

        log.info("Complete retrieval from Evidemki Psikoloji" + returner);
        return returner;
    }
}
