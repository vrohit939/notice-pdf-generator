package com.rv.noticepdfgenerator.repository;

import com.rv.noticepdfgenerator.model.NoticeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeTemplateRepository extends JpaRepository<NoticeTemplate, Long> {

}
