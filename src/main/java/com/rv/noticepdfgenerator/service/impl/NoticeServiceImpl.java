package com.rv.noticepdfgenerator.service.impl;

import com.rv.noticepdfgenerator.exceptions.NoticeNotFoundException;
import com.rv.noticepdfgenerator.model.Notice;
import com.rv.noticepdfgenerator.repository.NoticeRepository;
import com.rv.noticepdfgenerator.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public Notice getNoticeById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("Notice not found for ID: " + id));
    }

}
