package com.example.hms_backend.noticeBoard.service;


import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.repo.RoleRepo;
import com.example.hms_backend.noticeBoard.dto.NoticeDTO;
import com.example.hms_backend.noticeBoard.entity.Attachment;
import com.example.hms_backend.noticeBoard.entity.Notice;
import com.example.hms_backend.noticeBoard.mapper.NoticeMapper;
import com.example.hms_backend.noticeBoard.repo.NoticeRepo;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {


    private final NoticeRepo noticeRepo;
    private final NoticeMapper noticeMapper;
    private final RoleRepo roleRepo;

    public NoticeServiceImpl(NoticeRepo noticeRepo, NoticeMapper noticeMapper, RoleRepo roleRepo) {
        this.noticeRepo = noticeRepo;
        this.noticeMapper = noticeMapper;
        this.roleRepo = roleRepo;
    }

    @Override
    public void saveNotice(NoticeDTO noticeDTO) throws IOException {

        Set<Role> roles = new HashSet<>();
        if (noticeDTO.getTargetAudienceIds() != null && !noticeDTO.getTargetAudienceIds().isEmpty()) {
            roles = new HashSet<>(roleRepo.findAllById(noticeDTO.getTargetAudienceIds()));

        }
        Notice notice = noticeMapper.dtoToEntity(noticeDTO, roles);

        noticeRepo.save(notice);
    }

    //show list of notice
    @Override
    public List<NoticeDTO> getAllNotices() {

        LocalDateTime now = LocalDateTime.now(); // current date and time

        return noticeRepo.findAll().stream()
                .filter(n->n.getEndDate().isAfter(now))  //if endate is cross then that notice will not be fetch

                .map(n->
        {
            NoticeDTO noticeDTO = new NoticeDTO();
            try {
               noticeDTO= noticeMapper.entityToDto(n);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return noticeDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteNoticeById(Long id) throws IOException {
        noticeRepo.deleteById(id);
    }

    @Override
    public NoticeDTO getNoticeById(Long id) throws IOException {
        Notice notice = noticeRepo.findById(id).orElseThrow(()->new RuntimeException("Notice Not Found with Id :-"+id));
       NoticeDTO noticeDTO = noticeMapper.entityToDto(notice);
        return noticeDTO;
    }



    @Override
    public void updateNotice(Long id, NoticeDTO noticeDTO) throws IOException {

        Notice notice = noticeRepo.findById(id).orElseThrow(()->new RuntimeException("Notice Not Found with Id :-"+id));

        notice.setTitle(noticeDTO.getNoticeTitle());
        notice.setDescription(noticeDTO.getNoticeDescription());
        notice.setStartDate(LocalDateTime.parse(noticeDTO.getNoticeStartDate()));
        notice.setEndDate(LocalDateTime.parse(noticeDTO.getNoticeEndDate()));

        // Update target audience
        if (noticeDTO.getTargetAudienceIds() != null && !noticeDTO.getTargetAudienceIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepo.findAllById(noticeDTO.getTargetAudienceIds()));
            notice.setTargetAudience(roles);
        } else {
            notice.setTargetAudience(new HashSet<>()); // clear all if nothing selected
        }

        //  Handle file attachment if applicable
        if (noticeDTO.getAttachment() != null && !noticeDTO.getAttachment().isEmpty())
        {
             Attachment attachment = notice.getAttachment();

               if (attachment == null)
               {
                   attachment = new Attachment();
                   attachment.setNotice(notice);
               }



                   attachment.setFileName(noticeDTO.getAttachment().getOriginalFilename());
                   attachment.setData(noticeDTO.getAttachment().getBytes());
                   attachment.setFileType(noticeDTO.getAttachment().getContentType());

                   notice.addAttachment(attachment);


        }

        noticeRepo.save(notice);

    }


}
