package com.vehiclub.api.services.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class HtmlToPdfConverter {

    public byte[] convertHtmlToPdf(String htmlContent) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode(); // Enables faster parsing, might have minor rendering differences
        builder.withHtmlContent(htmlContent, null); // Base URI is null as we don't have external resources
        builder.toStream(os);
        builder.run();
        return os.toByteArray();
    }
}
