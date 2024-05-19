package io.ssafy.soupapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(name = "status", nullable = false)
    private boolean status;
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    /**
     * 데이터가 처음 추가 될 때
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.createdAt = now;
        this.modifiedAt = now;
        this.status = true;
    }

    /**
     * 데이터가 업데이트 될 때
     */
    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
