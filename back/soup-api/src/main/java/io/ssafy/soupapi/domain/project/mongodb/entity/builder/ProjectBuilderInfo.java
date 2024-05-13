package io.ssafy.soupapi.domain.project.mongodb.entity.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Builder
public class ProjectBuilderInfo {
    @Field("springboot_type")
    private String type;
    @Field("springboot_language")
    private String language;
    @Field("springboot_language_version")
    private String languageVersion;
    @Field("springboot_version")
    private String version;
    @Field("springboot_build_packaging")
    private SpringPackaging packaging;
    @Field("springboot_group")
    private String group;
    @Field("springboot_artifact")
    private String artifact;
    @Field("springboot_name")
    private String name;
    @Field("springboot_description")
    private String description;
    @Field("springboot_package_name")
    private String packageName;
    @Field("springboot_dependencies")
    private List<ProjectBuilderDependency> dependencies;
    @Field("springboot_file_path")
    private String filePath;
    @Field("springboot_zip_file_path")
    private String zipFilePath;
    @Field("springboot_s3_url")
    private String s3Url;
}
