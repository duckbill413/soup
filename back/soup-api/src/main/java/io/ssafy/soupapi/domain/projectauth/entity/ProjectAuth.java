package io.ssafy.soupapi.domain.projectauth.entity;

import io.ssafy.soupapi.domain.BaseEntity;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.entity.ProjectRole;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;
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
@SQLRestriction("project_auth_status=TRUE")
public class ProjectAuth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_auth_id")
    private UUID id;
    @Builder.Default
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "project_auth_role", joinColumns = @JoinColumn(name = "project_auth_id"))
    @Column(name = "role")
    private Set<ProjectRole> roles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "member_id")
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
