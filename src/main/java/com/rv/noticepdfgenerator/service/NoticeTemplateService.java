package com.rv.noticepdfgenerator.service;

import com.rv.noticepdfgenerator.model.NoticeTemplate;

public interface NoticeTemplateService {

    NoticeTemplate createTemplate(NoticeTemplate template);

    NoticeTemplate getTemplateById(Long id);

}
