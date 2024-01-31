package com.example.demo;

import com.example.demo.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class Chrome implements AutoCloseable {

    public ChromeOptions options;
    public WebDriver driver;

    CompanyRepository companyPostRepository;

    @Autowired
    public Chrome(CompanyRepository companyPostRepository) {
        this.companyPostRepository = companyPostRepository;
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
            driver.close();
        }
    }
}

