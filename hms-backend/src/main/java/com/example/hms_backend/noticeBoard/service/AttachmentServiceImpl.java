package com.example.hms_backend.noticeBoard.service;


import com.example.hms_backend.noticeBoard.entity.Attachment;
import com.example.hms_backend.noticeBoard.repo.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AttachmentServiceImpl implements AttachmentService {


    private final AttachmentRepo attachmentRepo;

    public AttachmentServiceImpl(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    //this method will send respone to controller for downloading image data
    @Override
    public ResponseEntity<byte[]> getAttachmentDownloadResponse(Long id) throws Exception {
        Attachment attachment = attachmentRepo.findById(id)
                .orElseThrow(() -> new Exception("Attachment not found with id: " + id));

        // Handle null fileType by providing a default
        String contentType = attachment.getFileType() != null ? attachment.getFileType() : "application/octet-stream";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(attachment.getData());
    }

    //this method will send respone to controller for view image data
    @Override
    public ResponseEntity<byte[]> getAttachmentViewResponse(Long id) throws Exception {

        Attachment attachment = new Attachment();
        attachment = attachmentRepo.findById(id).orElseThrow(() -> new Exception("Attachment not found with id: " + id));

        // Handle null fileType by providing a default
        String contentType = attachment.getFileType() != null ? attachment.getFileType() : "application/octet-stream";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\"" + attachment.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(attachment.getData());
    }
}
