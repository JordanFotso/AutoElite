package com.vehiclub.api.domain.documents;

public class HtmlDocument implements Document {
    private String type;
    private String content;

    public HtmlDocument(String type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String getType() {
        return "HTML - " + type;
    }

    @Override
    public String getContent() {
        return "<html><body><h1>HTML Content for " + type + "</h1><p>" + content + "</p></body></html>";
    }
}
