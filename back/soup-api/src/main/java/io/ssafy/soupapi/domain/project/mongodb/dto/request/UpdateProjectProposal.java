package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.ssafy.soupapi.domain.project.mongodb.entity.Proposal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "프로젝트 제안서 업데이트")
public record UpdateProjectProposal(
        @NotEmpty(message = "프로젝트 Id는 필수 입니다.")
        @Schema(description = "프로젝트 Id")
        String projectId,
        @Schema(description = "프로젝트 기획 배경")
        String background,
        @Schema(description = "프로젝트 서비스 소개")
        String introduce,
        @Schema(description = "프로젝트 서비스 타겟")
        String target,
        @Schema(description = "프로젝트 기대효과")
        String expectation
) {
    public static Proposal toProjectProposal(UpdateProjectProposal updateProjectProposal) {
        return Proposal.builder()
                .background(updateProjectProposal.background())
                .expectation(updateProjectProposal.expectation())
                .introduce(updateProjectProposal.introduce())
                .target(updateProjectProposal.target())
                .build();
    }
}
