package io.ssafy.soupapi.domain.projectbuilder.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_builder")
public class ProjectBuilder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_builder_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "project_builder_version", nullable = false)
    private BuilderVersion version;
    @Enumerated(EnumType.STRING)
    @Column(name = "project_builder_type", nullable = false)
    private BuilderType type;
    @Column(name = "project_builder_file_path", length = 500, nullable = false)
    private String filePath;
}
