package io.ssafy.soupapi.domain.springinfo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dependency")
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dependency_id")
    private Long id;
    @Column(name = "dependency_name", length = 100, nullable = false)
    private String name;
    @Column(name = "dependency_description", length = 500, nullable = false)
    private String description;
    @Column(name = "dependency_category", length = 20, nullable = false)
    private String category;
    @Column(name = "dependency_code", length = 2000, nullable = false)
    private String code;
    @Column(name = "basic_dependency", nullable = false)
    private boolean basic;
}
