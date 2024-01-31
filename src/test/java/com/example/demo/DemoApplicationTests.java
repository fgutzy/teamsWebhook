package com.example.demo;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
/*
    @Autowired
    CompanyRepository companyRepository;

    @Test
    void contextLoads() {
        System.out.println("start");
        String hiwell = "";

        try (Chrome chrome = new Chrome(companyRepository)) {
            System.out.println("inside try");
            try {
                hiwell = chrome.runWithChrome(chrome, "hiwell", WebsiteRetrieval::retrieveDataFromHiWell);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("all tasks completed");
            StringBuilder priceChangeMessage = new StringBuilder();
            String alertColor = "good";

            try {
                List<Company> allCompanies = (List<Company>) companyRepository.findAll();

                for (Company company : allCompanies) {
                    System.out.println("Current company: " + company);

                    if (company != null) {
                        String currentPrice = company.getCurrentPrice();
                        System.out.println("Current price of " + company + " is: " + currentPrice);

                        String lastPrice = company.getLastPrice();
                        System.out.println("Last price of " + company + " was: " + lastPrice);

                        if (!lastPrice.equals(currentPrice)) {
                            System.out.println("Price changes detected for " + company + " last price: " + lastPrice
                                    + " current price: " + currentPrice);

                            if (priceChangeMessage.length() > 0) {
                                priceChangeMessage.append("\n");
                            }
                            priceChangeMessage.append("Price for ")
                                    .append(company).append(" was ")
                                    .append(company.getLastPrice())
                                    .append(" on the ")
                                    .append(company.getDateOfLastChange());
                            System.out.println("price for " + company.getName() + " was updated from " + company.getLastPrice() + " to " + company.getCurrentPrice());
                            companyRepository.updateLastPriceByName(companyRepository.findCompanyByName(company.getName()).getCurrentPrice(), company.getName());
                            companyRepository.updateDateByName(new SimpleDateFormat("dd/MM/yy").format(new Date()), company.getName());
                            System.out.println("date for " + company.getName() + " was updated to " + company.getDateOfLastChange());
                        }
                    }
                }
                if (priceChangeMessage.isEmpty()) {
                    priceChangeMessage = new StringBuilder("no changes in prices since last check");
                } else { // changes were detected, so the change message should appear red
                    alertColor = "attention";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("send post request");
                HttpRequest postRequest;
                postRequest = HttpRequest.newBuilder()
                        .uri(new URI(Constants.incomingWebhookURI))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(Constants.json.
                                formatted(new SimpleDateFormat("dd/MM/yy").format(new Date()), hiwell,
                                        "noraPsikoloji", "psikolojiMerkezi", priceChangeMessage.toString(), alertColor)))
                        .build();
                HttpClient httpClient = HttpClient.newHttpClient();
                httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception ignored) {
        }
    }

    @Test
    public void updatePrice() {
        companyRepository.updateLastPriceByName("10 Lira for 56 min", "hiwell");
        companyRepository.updateDateByName(new SimpleDateFormat("dd/MM/yy").format(new Date()), "hiwell");
    }

    @Test
    public void checkForDifferences() {
        List<Company> allCompanies = (List<Company>) companyRepository.findAll();

        for (Company company : allCompanies) {
            System.out.println("Current company: " + company);

            if (company != null) {
                String currentPrice = company.getCurrentPrice();
                System.out.println("Current price of " + company + " is: " + currentPrice);

                String lastPrice = company.getLastPrice();
                System.out.println("Last price of " + company + " was: " + lastPrice);

                if (!lastPrice.equals(currentPrice)) {
                    System.out.println("Price changes detected for " + company + " last price: " + lastPrice
                            + " current price: " + currentPrice);

                    companyRepository.updateLastPriceByName(companyRepository.findCompanyByName(company.getName()).getCurrentPrice(), company.getName());
                    companyRepository.updateDateByName(new SimpleDateFormat("dd/MM/yy").format(new Date()), company.getName());
                }
            }
        }
    }*/
}