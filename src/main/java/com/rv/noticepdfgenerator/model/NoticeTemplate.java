package com.rv.noticepdfgenerator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "templates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoticeTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String templateName;

    @Lob
    private String htmlContent;

}
