package com.example.demo;

import java.util.HashMap;

public class Constants {

    public static final String incomingWebhookURI = System.getenv("INCOMING_WEBHOOK_URI");

    public static final String hiWellEmail = System.getenv("HIWELL_EMAIL");
    public static final String hiWellPassword = System.getenv("HIWELL_PASSWORD");

    public static HashMap<String, String> currentPrice = new HashMap<>();
    public static HashMap<String, String> lastPrice = new HashMap<>();

    public static HashMap<String, String> dateOfLastPrice = new HashMap<>();

    static{
        lastPrice.put("nora psikoloji","1 500.00 TL for 50dk");
        dateOfLastPrice.put("nora psikoloji", "30/10/23");

        lastPrice.put("hiwell", "799 TL for 50 Min");
        dateOfLastPrice.put("hiwell", "30/10/23");

        lastPrice.put("psikoloji merkezi", "400 TL - 1300 TL for 50 Dakika");
        dateOfLastPrice.put("psikoloji merkezi", "30/10/23");

        /*lastPrice.put("evidemki psikoloji", "400₺ - 1600₺");
        dateOfLastPrice.put("evidemki psikoloji", "30/10/23");*/
    }
    public static final String json = """
            {
                "type": "message",
                "attachments": [
                    {
                        "contentType": "application/vnd.microsoft.card.adaptive",
                        "contentUrl": null,
                        "content": {
                            "$schema": "http://adaptivecards.io/schemas/adaptive-card.json",
                            "type": "AdaptiveCard",
                            "version": "1.2",
                            "body": [
                                {
                                    "type": "TextBlock",
                                    "size": "ExtraLarge",
                                    "weight": "Lighter",
                                    "text": "Current prices"
                                },
                                {
                                    "type": "TextBlock",
                                    "text": "%s"
                                },
                                {
                                    "type": "FactSet",
                                    "facts": [
                                        {
                                            "title": "HiWell",
                                            "value": "%s"
                                        },
                                        {
                                            "title": "Nora Psikoloji",
                                            "value": "%s"
                                        },
                                        {
                                            "title": "Psikoloji Merkezi",
                                            "value": "%s"
                                        }
                                    ]
                                },
                                {
                                    "type": "TextBlock",
                                    "size": "Medium",
                                    "weight": "Bolder",
                                    "text": "Changes:"
                                },
                                {
                                    "type": "RichTextBlock",
                                    "inlines": [
                                         {
                                            "type": "TextRun",
                                            "text": "%s",
                                            "color": "%s"
                                         }
                                    ]
                                }
                            ]
                        }
                    }
                ]
            }
                   """;
}
