package com.rv.noticepdfgenerator.controller;

import com.rv.noticepdfgenerator.service.PdfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfController {

    private final PdfService pdfService;

    private static final Logger log = LoggerFactory.getLogger(PdfController.class);

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/generate/{noticeId}")
    public CompletableFuture<ResponseEntity<byte[]>> generateNotice(@PathVariable Long noticeId) {
        log.info("Received request to generate notice PDF for noticeId: {}", noticeId);

        return pdfService.generateNoticePdf(noticeId)
                .thenApply(pdf -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=notice_" + noticeId + ".pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdf));
    }


}
