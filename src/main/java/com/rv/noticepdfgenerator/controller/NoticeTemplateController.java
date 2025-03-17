package com.rv.noticepdfgenerator.controller;

import com.rv.noticepdfgenerator.model.NoticeTemplate;
import com.rv.noticepdfgenerator.service.NoticeTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/templates")
public class NoticeTemplateController {

    private final NoticeTemplateService templateService;

    public NoticeTemplateController(NoticeTemplateService noticeTemplateService) {
        this.templateService = noticeTemplateService;
    }

    @PostMapping
    public ResponseEntity<NoticeTemplate> createTemplate(@RequestBody NoticeTemplate template) {
        return ResponseEntity.ok(templateService.createTemplate(template));
    }

}
