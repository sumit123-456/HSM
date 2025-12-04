package com.example.hms_backend.noticeBoard.repo;


import com.example.hms_backend.noticeBoard.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepo extends JpaRepository<Notice, Long> {
}
