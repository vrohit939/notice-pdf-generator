package com.rv.noticepdfgenerator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "notices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;

    private String address;

    private String phone;

    private String referenceNumber;

    private LocalDate dueDate;

    // Multiple Notice records can be associated with a single NoticeTemplate
    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private NoticeTemplate template;  // Links to the stored HTML template

}
