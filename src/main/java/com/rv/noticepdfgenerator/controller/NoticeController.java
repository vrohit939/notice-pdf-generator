package com.rv.noticepdfgenerator.controller;

import com.rv.noticepdfgenerator.model.Notice;
import com.rv.noticepdfgenerator.model.NoticeDto;
import com.rv.noticepdfgenerator.model.NoticeTemplate;
import com.rv.noticepdfgenerator.service.NoticeService;
import com.rv.noticepdfgenerator.service.NoticeTemplateService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

    private final NoticeService noticeService;

    private final NoticeTemplateService noticeTemplateService;

    public NoticeController(NoticeService noticeService, NoticeTemplateService noticeTemplateService) {
        this.noticeService = noticeService;
        this.noticeTemplateService = noticeTemplateService;
    }

    @PostMapping
    public ResponseEntity<NoticeDto> createNotice(@Valid @RequestBody NoticeDto noticeDto) {
        log.info("Creating notice for recipient: {}", noticeDto.getRecipientName());

        Notice notice = mapToEntity(noticeDto);
        Notice savedNotice = noticeService.createNotice(notice);

        return ResponseEntity.ok(mapToDto(savedNotice));
    }

    /*
     *
     * Below are helper methods
     *
     */

    // Convert DTO -> Entity for saving
    private Notice mapToEntity(NoticeDto dto) {
        Notice notice = new Notice();
        notice.setId(dto.getId());
        notice.setRecipientName(dto.getRecipientName());
        notice.setAddress(dto.getAddress());
        notice.setPhone(dto.getPhone());
        notice.setReferenceNumber(dto.getReferenceNumber());
        notice.setDueDate(dto.getDueDate());

        if (dto.getTemplate() != null && dto.getTemplate().getId() != null) {
            NoticeTemplate template = noticeTemplateService.getTemplateById(dto.getTemplate().getId());
            notice.setTemplate(template);
        }

        return notice;
    }

    // Convert Entity -> DTO for response
    private NoticeDto mapToDto(Notice notice) {
        NoticeDto.TemplateSummary templateSummary = null;

        if (notice.getTemplate() != null) {
            templateSummary = new NoticeDto.TemplateSummary(
                    notice.getTemplate().getId(),
                    notice.getTemplate().getTemplateName()
            );
        }

        return new NoticeDto(
                notice.getId(),
                notice.getRecipientName(),
                notice.getAddress(),
                notice.getPhone(),
                notice.getReferenceNumber(),
                notice.getDueDate(),
                templateSummary
        );
    }

}
