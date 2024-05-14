package io.ssafy.soupapi.domain.noti.entity;

import io.ssafy.soupapi.domain.BaseEntity;
import io.ssafy.soupapi.domain.chat.entity.Chat;
import io.ssafy.soupapi.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

// PostgreSQL에 알림을 저장하기 위해 만든 entity 지만
// 알림은 MongoDB에 저장하게 되어 사용하지 않게 된 class

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "noti")
@AttributeOverrides({
        @AttributeOverride(name = "status", column = @Column(name = "noti_status")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "noti_created_at")),
        @AttributeOverride(name = "modifiedAt", column = @Column(name = "noti_modified_at"))
})
@SQLRestriction("noti_status=TRUE")
public class Noti extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id")
    private Long id;
    @Column(name = "noti_title", length = 100, nullable = false)
    private String title;
    @Column(name = "noti_is_read", nullable = false)
    private boolean isRead;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    /**
     * Member Entity 설정
     * Member - Noti Entity 간 일관성 유지
     *
     * @param member member element to be set
     */
    public void setMember(Member member) {
        this.member = member;
        if (!member.getNotiList().contains(this)) {
            member.getNotiList().add(this);
        }
    }

    /**
     * Chat Entity 설정
     * Chat - Noti Entity 간 일관성 유지
     *
     * @param chat chat element to be set
     */
    public void setChat(Chat chat) {
        this.chat = chat;
        if (!chat.getNotiList().contains(this)) {
            chat.getNotiList().add(this);
        }
    }
}
