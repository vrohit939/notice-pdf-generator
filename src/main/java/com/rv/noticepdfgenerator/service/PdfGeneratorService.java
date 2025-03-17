package com.rv.noticepdfgenerator.service;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class PdfGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(PdfGeneratorService.class);

    @Async("pdfTaskExecutor")
    public CompletableFuture<byte[]> generatePdf(String htmlContent) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // Apply external CSS correctly
            String fullHtml = wrapHtmlWithCss(htmlContent);

            // Build PDF using OpenHTMLToPDF
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.usePdfAConformance(PdfRendererBuilder.PdfAConformance.PDFA_1_A);

            // Register Fonts from Classpath
            registerFont(builder, "static/fonts/NotoSansDevanagari-Regular.ttf", "NotoSansDevanagari");
            registerFont(builder, "static/fonts/ArialUnicodeMS.ttf", "ArialUnicodeMS");
            registerFont(builder, "static/fonts/NotoSans-Regular.ttf", "NotoSans");
            registerFont(builder, "static/fonts/DejaVuSans.ttf", "DejaVuSans");

            builder.withHtmlContent(fullHtml, null);
            builder.toStream(outputStream);
            builder.run();

            return CompletableFuture.completedFuture(outputStream.toByteArray());
        }
    }

    // Helper method to register fonts from classpath
    private void registerFont(PdfRendererBuilder builder, String fontPath, String fontFamily) throws IOException {
        ClassPathResource fontResource = new ClassPathResource(fontPath);
        if (fontResource.exists()) {
            builder.useFont(fontResource.getFile(), fontFamily, 400, BaseRendererBuilder.FontStyle.NORMAL, true);
        } else {
            log.warn("Font not found: {}", fontPath);
        }
    }

    // Wrap HTML with external CSS
    private String wrapHtmlWithCss(String htmlContent) {
        return "<html><head>"
                + "<meta charset='UTF-8' />"
                + "<link rel='stylesheet' type='text/css' href='classpath:static/css/styles.css' />"
                + "</head><body>" + htmlContent + "</body></html>";
    }

}
