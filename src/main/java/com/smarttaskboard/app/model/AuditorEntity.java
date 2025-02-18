package com.smarttaskboard.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditorEntity {
    @CreatedDate
    @Column(name = "CreatedOn")
    private LocalDateTime createdOn;

    @CreatedBy
    @Column(name = "CreatedBy", length = 50)
    private String createdBy;


    @LastModifiedDate
    @Column(name = "UpdatedOn")
    private LocalDateTime updatedOn;

    @LastModifiedBy
    @Column(name = "UpdatedBy", length = 50)
    private String updatedBy;

    @Column(name = "DeletedOn")
    private LocalDateTime deletedOn;

    @Column(name = "DeletedBy", length = 50)
    private String deletedBy;

    @Column(name = "isDeleted", length = 50)
    private Boolean isDeleted = false;
}
