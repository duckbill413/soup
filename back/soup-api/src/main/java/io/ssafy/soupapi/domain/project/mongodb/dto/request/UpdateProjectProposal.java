package io.ssafy.soupapi.domain.project.mongodb.dto.request;

import io.ssafy.soupapi.domain.project.mongodb.entity.Proposal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로젝트 제안서 업데이트")
public record UpdateProjectProposal(
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
