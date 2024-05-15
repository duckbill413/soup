package io.ssafy.soupapi.usecase.dto.liveblock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectInfo;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectTool;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record LiveUpdateProjectInfo(
        @JsonProperty("project_name")
        String projectName,
        @JsonProperty("project_description")
        String projectDescription,
        @JsonProperty("project_photo")
        String projectPhoto,
        @JsonProperty("project_startDate")
        String projectStartDate,
        @JsonProperty("project_endDate")
        String projectEndDate,
        @JsonProperty("project_tools")
        List<LiveProjectTool> projectTools,
        @JsonProperty("project_team")
        List<LiveProjectMember> projectTeam
) {
    public LiveUpdateProjectInfo {
        if (Objects.isNull(projectTools)) {
            projectTools = List.of();
        }
        if (Objects.isNull(projectTeam)) {
            projectTeam = List.of();
        }
    }

    public static UpdateProjectInfo toUpdateProjectInfo(LiveUpdateProjectInfo projectInfo) {
        var tools = projectInfo.projectTools().stream().map(t -> UpdateProjectTool.builder()
                .toolName(t.name())
                .toolUrl(t.url())
                .build()).toList();
        return UpdateProjectInfo.builder()
                .name(projectInfo.projectName())
                .description(projectInfo.projectDescription())
                .imgUrl(projectInfo.projectPhoto())
                .startDate(Instant.parse(projectInfo.projectStartDate()))
                .endDate(Instant.parse(projectInfo.projectEndDate()))
                .tools(tools)
                .build();
    }

}
