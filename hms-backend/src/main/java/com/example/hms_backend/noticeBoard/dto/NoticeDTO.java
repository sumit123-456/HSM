package com.example.hms_backend.noticeBoard.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class NoticeDTO {

    private Long noticeId;

    private Long attachmentId;

    @NotBlank
    private String noticeTitle;

    @NotBlank
    private String noticeDescription; // stores HTML from CKEditor

    private String noticeStartDate;
    private String noticeEndDate;

    // store selected role IDs from the form
    private Set<Long> targetAudienceIds = new HashSet<>();

    // optionally, for display purposes in the UI
    private Set<String> targetAudienceNames = new HashSet<>();

    private MultipartFile attachment;

    // For display
    private String attachmentName;
    private String attachmentType;

}
