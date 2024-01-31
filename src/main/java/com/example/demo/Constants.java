package com.example.demo;

import java.util.HashMap;

public class Constants {
    //new private channel
    public static final String incomingWebhookURI = "https://hidoctor.webhook.office.com/webhookb2/ffd42cf1-bb1b-4f8d-b321-f5c74e495b44@5efb6354-0941-427d-97e0-741b890f277f/IncomingWebhook/d29c5c83ecb248b9bb4070deeecceb1e/f6127a50-0e37-4ed1-8a79-0f321f15c144";

    public static final String hiWellEmail = System.getenv("HIWELL_EMAIL");
    public static final String hiWellPassword = System.getenv("HIWELL_PASSWORD");

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
                                            "title": "Mantra Care",
                                            "value": "%s"
                                        },
                                        {
                                            "title": "Nora Psikoloji",
                                            "value": "%s"
                                        },
                                        {
                                            "title": "Psikoloji Merkezi",
                                            "value": "%s"
                                        },
                                        {
                                            "title": "Psikolog Ofisi",
                                            "value": "%s"
                                        },
                                        {
                                            "title": "Terappin",
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
