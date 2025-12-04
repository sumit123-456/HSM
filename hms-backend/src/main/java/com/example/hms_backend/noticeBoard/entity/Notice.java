package com.example.hms_backend.noticeBoard.entity;


import com.example.hms_backend.audit.Auditable;
import com.example.hms_backend.authentication.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "notice")
public class Notice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description; // stores HTML from CKEditor

    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @ManyToMany
    @JoinTable(name = "notice_role",
            joinColumns = @JoinColumn(name = "notice_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> targetAudience = new HashSet<>();


    @OneToOne(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Attachment attachment;


    // helper to keep both sides in sync
    public void addAttachment(Attachment attachment) {
        this.attachment=attachment;
        attachment.setNotice(this);
    }


}

