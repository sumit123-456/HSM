package com.example.hms_backend.noticeBoard.controller;


import com.example.hms_backend.noticeBoard.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) throws Exception {
        return attachmentService.getAttachmentDownloadResponse(id);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewAttachment(@PathVariable Long id) throws Exception {
        return attachmentService.getAttachmentViewResponse(id);
    }
}
