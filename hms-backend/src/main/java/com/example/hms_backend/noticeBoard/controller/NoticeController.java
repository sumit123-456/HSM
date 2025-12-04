package com.example.hms_backend.noticeBoard.controller;


import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.service.RoleService;
import com.example.hms_backend.noticeBoard.dto.NoticeDTO;
import com.example.hms_backend.noticeBoard.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final RoleService roleService;
    private final NoticeService noticeService;

    public NoticeController(RoleService roleService, NoticeService noticeService) {
        this.roleService = roleService;
        this.noticeService = noticeService;
    }

    //  Get all active notices
    @GetMapping
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    //  Get a single notice by ID
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> getNoticeById(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(noticeService.getNoticeById(id));
    }

    //  Create new Notice with File Upload
    @PostMapping(consumes = {"multipart/form-data"})
//    @PreAuthorize("hasAuthority('NOTICE_ADD')")
    public ResponseEntity<String> createNotice(
            @Valid @ModelAttribute NoticeDTO noticeDTO
    ) throws IOException {
        noticeService.saveNotice(noticeDTO);
        return ResponseEntity.ok("Notice added successfully");
    }

    // ✅ Update Notice with optional new attachment
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<String> updateNotice(
            @PathVariable Long id,
            @Valid @ModelAttribute NoticeDTO noticeDTO
    ) throws IOException {
        noticeService.updateNotice(id, noticeDTO);
        return ResponseEntity.ok("Notice updated successfully");
    }

    // ✅ Delete a Notice
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) throws IOException {
        noticeService.deleteNoticeById(id);
        return ResponseEntity.ok("Notice deleted successfully");
    }

    // ✅ Get all roles (for dropdowns in UI)
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
