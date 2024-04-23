package io.ssafy.soupapi.domain.dependency;

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
    @Column(name = "dependency_version", length = 20, nullable = false)
    private String version;
    @Column(name = "dependency_code", nullable = false)
    private String code;
}
