package com.example.demo;

import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.function.BiFunction;

public class Chrome implements AutoCloseable {

    public ChromeOptions options;
    public WebDriver driver;


    public Chrome() {
        System.setProperty("webdriver.chrome.driver", System.getenv("PATH_TO_CHROME_DRIVER"));
        options = new ChromeOptions();
        options.setBinary(System.getenv("GOOGLE_CHROME_BIN"));
        options.addArguments("--window-size=2560,1440");
        options.addArguments("--disable-notifications");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }


    @Override
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static String runWithChrome(Chrome chrome, String company, BiFunction<Chrome, String, String> method) {
        String returnValue = "";
        try {
            returnValue =  method.apply(chrome, company);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Constants.currentPrice.put(company, returnValue);
        }
        return returnValue;
    }
}

