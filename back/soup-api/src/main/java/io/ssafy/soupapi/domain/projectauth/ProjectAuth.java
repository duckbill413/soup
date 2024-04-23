package io.ssafy.soupapi.domain.projectauth;

import io.ssafy.soupapi.domain.BaseEntity;
import io.ssafy.soupapi.domain.member.Member;
import io.ssafy.soupapi.domain.project.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "project_auth")
@AttributeOverrides({
        @AttributeOverride(name = "status", column = @Column(name = "project_auth_status")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "project_auth_created_at")),
        @AttributeOverride(name = "modifiedAt", column = @Column(name = "project_auth_modified_at"))
})
@SQLRestriction("status=TRUE")
public class ProjectAuth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_auth_id")
    private UUID id;
    @Column(name = "project_auth_position")
    private String position; // TODO: Enum으로 변경 필요
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * Member Entity 설정
     * Member - ProjectAuth Entity 간 일관성 유지
     *
     * @param member member element to be set
     */
    public void setMember(Member member) {
        this.member = member;
        if (!member.getProjectAuthList().contains(this)) {
            member.getProjectAuthList().add(this);
        }
    }

    /**
     * Project Entity 설정
     * ProjectAuth - Project Entity 간 일관성 유지
     *
     * @param project project element to be set
     */
    public void setProject(Project project) {
        this.project = project;
        if (!project.getProjectAuthList().contains(this)) {
            project.getProjectAuthList().add(this);
        }
    }
}
