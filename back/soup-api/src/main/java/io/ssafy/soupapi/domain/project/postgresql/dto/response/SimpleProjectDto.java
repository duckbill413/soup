package io.ssafy.soupapi.domain.project.postgresql.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Simple 프로젝트 정보")
public class SimpleProjectDto {

        @Schema(description = "프로젝트 ID")
        private String id;
        @Schema(description = "프로젝트 이름")
        private String name;
        @Schema(description = "프로젝트 이미지 URL")
        private String imgUrl;

}
