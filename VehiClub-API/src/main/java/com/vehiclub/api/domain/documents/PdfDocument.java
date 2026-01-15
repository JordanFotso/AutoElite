package com.vehiclub.api.domain.documents;

import java.util.Base64;

public class PdfDocument implements Document {
    private String type;
    private String base64Content; // Stored as Base64 String to fit the Document interface, also for persistence
    private byte[] binaryContent; // The actual PDF bytes for generation/download

    public PdfDocument(String type, byte[] binaryContent) {
        this.type = type;
        this.binaryContent = binaryContent;
        this.base64Content = Base64.getEncoder().encodeToString(binaryContent);
    }

    // Constructor for deserialization if content is already Base64 string
    public PdfDocument(String type, String base64Content) {
        this.type = type;
        this.base64Content = base64Content;
        this.binaryContent = Base64.getDecoder().decode(base64Content);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getContent() {
        return base64Content;
    }

    public byte[] getBinaryContent() {
        return binaryContent;
    }
}