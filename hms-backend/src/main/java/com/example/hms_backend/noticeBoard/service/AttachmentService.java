package com.example.hms_backend.noticeBoard.service;


import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AttachmentService {

    ResponseEntity<byte[]> getAttachmentDownloadResponse(Long id) throws Exception;

    ResponseEntity<byte[]> getAttachmentViewResponse(Long id) throws Exception;
}
