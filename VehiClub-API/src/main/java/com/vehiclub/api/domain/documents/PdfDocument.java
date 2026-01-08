package com.vehiclub.api.domain.documents;

public class PdfDocument implements Document {
    private String type;
    private String content;

    public PdfDocument(String type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public String getType() {
        return "PDF - " + type;
    }

    @Override
    public String getContent() {
        return "PDF Content for " + type + ": " + content;
    }
}
