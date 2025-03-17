package com.rv.noticepdfgenerator.service;

import com.rv.noticepdfgenerator.model.Notice;

public interface NoticeService {

    Notice createNotice(Notice notice);

    Notice getNoticeById(Long id);

}
