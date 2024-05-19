package io.ssafy.soupapi.domain.member.entity;

import io.ssafy.soupapi.domain.BaseEntity;
import io.ssafy.soupapi.domain.member.dto.MemberInfoDto;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Member.
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "member")
@AttributeOverrides({
        @AttributeOverride(name = "status", column = @Column(name = "member_status")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "member_created_at")),
        @AttributeOverride(name = "modifiedAt", column = @Column(name = "member_modified_at"))
})
@SQLRestriction("member_status=TRUE")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id")
    private UUID id;

    @Column(name = "member_nickname", length = 20)
    private String nickname;

    @Column(name = "member_phone", length = 20)
    private String phone;

    @Column(name = "member_email", length = 50, nullable = false)
    private String email;

    @Column(name = "member_profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "member_social_id", nullable = false)
    private String socialId;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<ProjectAuth> projectAuthList = new ArrayList<>();


    /**
     * ProjectAuth Entity 추가 메서드
     * Member - ProjectAuth Entity 사이 일관성 유지
     *
     * @param projectAuth projectAuth element to be appended to this list
     */
    public void addProjectAuth(ProjectAuth projectAuth) {
        this.projectAuthList.add(projectAuth);
        if (projectAuth.getMember() != this) {
            projectAuth.setMember(this);
        }
    }

    public MemberInfoDto toMemberInfoDto() {
        return MemberInfoDto.builder()
                .memberId(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .phone(this.phone)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
