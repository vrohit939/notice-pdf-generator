package com.rv.noticepdfgenerator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoticeDto {

    private Long id;

    @NotBlank(message = "Recipient name is required")
    private String recipientName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Reference number is required")
    private String referenceNumber;

    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    private TemplateSummary template;  // Only ID & name for response

    @Getter
    public static class TemplateSummary {
        private Long id;
        private String templateName;

        public TemplateSummary(Long id, String templateName) {
            this.id = id;
            this.templateName = templateName;
        }

    }

}
