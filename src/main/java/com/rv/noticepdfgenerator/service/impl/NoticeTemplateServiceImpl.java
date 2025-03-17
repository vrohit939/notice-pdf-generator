package com.rv.noticepdfgenerator.service.impl;

import com.rv.noticepdfgenerator.exceptions.NoticeNotFoundException;
import com.rv.noticepdfgenerator.model.NoticeTemplate;
import com.rv.noticepdfgenerator.repository.NoticeTemplateRepository;
import com.rv.noticepdfgenerator.service.NoticeTemplateService;
import org.springframework.stereotype.Service;

@Service
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    private final NoticeTemplateRepository noticeTemplateRepository;

    public NoticeTemplateServiceImpl(NoticeTemplateRepository noticeTemplateRepository) {
        this.noticeTemplateRepository = noticeTemplateRepository;
    }

    @Override
    public NoticeTemplate createTemplate(NoticeTemplate template) {
        return noticeTemplateRepository.save(template);
    }

    @Override
    public NoticeTemplate getTemplateById(Long id) {
        return noticeTemplateRepository.findById(id)
                .orElseThrow(()-> new NoticeNotFoundException("No template found for the given id : "+ id));
    }

}
