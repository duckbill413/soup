package io.ssafy.soupapi.domain.project.postgresql.entity;

import io.ssafy.soupapi.domain.BaseEntity;
import io.ssafy.soupapi.domain.projectauth.entity.ProjectAuth;
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
@Table(name = "project")
@AttributeOverrides({
        @AttributeOverride(name = "status", column = @Column(name = "project_status")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "project_created_at")),
        @AttributeOverride(name = "modifiedAt", column = @Column(name = "project_modified_at"))
})
@SQLRestriction("project_status=TRUE")
public class Project extends BaseEntity {
    @Id
    @Column(name = "project_id")
    private String id;
    @Column(name = "project_name")
    private String name;
    @Column(name = "project_img_url", length = 1000)
    private String imgUrl;
    @Column(name = "project_file_uri", length = 1000)
    private String fileUri;
    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectAuth> projectAuthList = new ArrayList<>();

    /**
     * ProjectAuth Entity 추가 메서드
     * Project - ProjectAuth Entity 사이 일관성 유지
     *
     * @param projectAuth project element to be appended to this list
     */
    public void addProjectAuth(ProjectAuth projectAuth) {
        this.projectAuthList.add(projectAuth);

        if (projectAuth.getProject() != this) {
            projectAuth.setProject(this);
        }
    }
}
