package com.example.hms_backend.noticeBoard.mapper;


import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.noticeBoard.dto.NoticeDTO;
import com.example.hms_backend.noticeBoard.entity.Attachment;
import com.example.hms_backend.noticeBoard.entity.Notice;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NoticeMapper {

    public Notice dtoToEntity(NoticeDTO noticeDTO, Set<Role> roles) throws IOException {
        Notice notice = new Notice();
        notice.setDescription(noticeDTO.getNoticeDescription());
        notice.setTitle(noticeDTO.getNoticeTitle());

        // Convert date strings to LocalDateTime
        notice.setEndDate(LocalDateTime.parse(noticeDTO.getNoticeEndDate()));
        notice.setStartDate(LocalDateTime.parse(noticeDTO.getNoticeStartDate()));

        // Set roles
        notice.setTargetAudience(roles);

        // Handle attachment
        if (noticeDTO.getAttachment() != null) {
            Attachment attachment = new Attachment();
            attachment.setFileName(noticeDTO.getAttachment().getOriginalFilename());
            attachment.setFileType(noticeDTO.getAttachment().getContentType());
            attachment.setData(noticeDTO.getAttachment().getBytes());
            attachment.setNotice(notice);
            notice.addAttachment(attachment);
        }

        return notice;
    }

    public NoticeDTO entityToDto(Notice notice) throws IOException {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeId(notice.getId());
        noticeDTO.setNoticeTitle(notice.getTitle());

        if (notice.getAttachment() != null) {
            noticeDTO.setAttachmentId(notice.getAttachment().getId());
        }

        // Map role IDs
        noticeDTO.setTargetAudienceIds(
                notice.getTargetAudience().stream()
                        .map(Role::getRoleId)
                        .collect(Collectors.toSet())
        );

        // Format date for form fields
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        noticeDTO.setNoticeStartDate(notice.getStartDate().format(formatter));
        noticeDTO.setNoticeEndDate(notice.getEndDate().format(formatter));
        noticeDTO.setNoticeDescription(notice.getDescription());

        // ✅ Map attachment (file data)
        if (notice.getAttachment() != null)
        {
            var attachment = notice.getAttachment();
            noticeDTO.setAttachmentId(attachment.getId());
            noticeDTO.setAttachmentName(attachment.getFileName());
            noticeDTO.setAttachmentType(attachment.getFileType());



        }


        return noticeDTO;
    }
}
