package com.rv.noticepdfgenerator.service;

import com.rv.noticepdfgenerator.exceptions.BadRequestException;
import com.rv.noticepdfgenerator.exceptions.NoticeNotFoundException;
import com.rv.noticepdfgenerator.exceptions.PdfGenerationException;
import com.rv.noticepdfgenerator.model.Notice;
import com.rv.noticepdfgenerator.model.NoticeTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PdfService {

    private static final Logger log = LoggerFactory.getLogger(PdfService.class);

    private final NoticeService noticeService;

    private final TemplateService templateService;

    private final PdfGeneratorService pdfGeneratorService;

    public PdfService(NoticeService noticeService, TemplateService templateService, PdfGeneratorService pdfGeneratorService) {
        this.noticeService = noticeService;
        this.templateService = templateService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @Async("pdfTaskExecutor")
    public CompletableFuture<byte[]> generateNoticePdf(Long noticeId) {
        log.info("Fetching notice details for ID: {}", noticeId);

        Notice notice = noticeService.getNoticeById(noticeId);
        if (notice == null) {
            throw new NoticeNotFoundException("Notice not found for ID: " + noticeId);
        }

        NoticeTemplate template = notice.getTemplate();
        if (template == null) {
            throw new BadRequestException("No template assigned to this notice");
        }

        // Prepare Data for Template Processing
        Map<String, Object> data = new HashMap<>();
        data.put("name", notice.getRecipientName());
        data.put("referenceNumber", notice.getReferenceNumber());
        data.put("address", notice.getAddress());
        data.put("phone", notice.getPhone());
        data.put("dueDate", notice.getDueDate() != null ? notice.getDueDate().toString() : "N/A");

        // Generate HTML
        log.debug("Generating HTML content for noticeId: {}", noticeId);
        String htmlContent = templateService.generateHtmlFromTemplate(template.getHtmlContent(), data);

        if (htmlContent == null || htmlContent.isEmpty()) {
            throw new PdfGenerationException("Generated HTML content is empty");
        }

        // Generate PDF
        try {
            log.info("Generating PDF for noticeId: {}", noticeId);
            return pdfGeneratorService.generatePdf(htmlContent);
        } catch (IOException e) {
            log.error("IOException while generating PDF for noticeId {}: {}", noticeId, e.getMessage(), e);
            throw new PdfGenerationException("Error generating PDF", e);
        } catch (Exception e) {
            log.error("Unexpected error while generating PDF for noticeId {}: {}", noticeId, e.getMessage(), e);
            throw new PdfGenerationException("Unexpected error occurred while generating PDF", e);
        }
    }

}
