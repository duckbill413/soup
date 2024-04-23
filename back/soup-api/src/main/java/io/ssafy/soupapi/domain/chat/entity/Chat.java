package io.ssafy.soupapi.domain.chat.entity;

import io.ssafy.soupapi.domain.BaseEntity;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.noti.entity.Noti;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "chat")
@AttributeOverrides({
        @AttributeOverride(name = "status", column = @Column(name = "chat_status")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "chat_created_at")),
        @AttributeOverride(name = "modifiedAt", column = @Column(name = "chat_modified_at"))
})
@SQLRestriction("chat_status=TRUE")
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;
    @Column(name = "chat_content", nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private List<Noti> notiList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    /**
     * Member Entity 설정
     * Member - Chat Entity 간 일관성 유지
     *
     * @param member member element to be set
     */
    public void setMember(Member member) {
        this.member = member;
        if (!member.getChatList().contains(this)) {
            member.getChatList().add(this);
        }
    }

    /**
     * Noti Entity 추가 메서드
     * Chat - Noti Entity 사이 일관성 유지
     *
     * @param noti noti element to be appended to this list
     */
    public void addNoti(Noti noti) {
        this.notiList.add(noti);
        if (noti.getChat() != this) {
            noti.setChat(this);
        }
    }

    /**
     * Project Entity 설정
     * Project - Chat Entity 간 일관성 유지
     *
     * @param project project element to be set
     */
    public void setProject(Project project) {
        this.project = project;
        if (!project.getChatList().contains(this)) {
            project.getChatList().add(this);
        }
    }
}
