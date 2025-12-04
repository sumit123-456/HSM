package com.example.hms_backend.noticeBoard.repo;


import com.example.hms_backend.noticeBoard.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepo extends JpaRepository<Attachment,Long>
{

}
