package dev.ctc.learning.rappellemoiapi.auditing;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditableEntity {

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date modifiedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
}
