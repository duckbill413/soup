package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프로젝트 기획서 DTO")
public record GetProjectProposal(
        String id,
        String background,
        String introduce,
        String target,
        String expectation
) {
    @Builder
    public GetProjectProposal {
        id = StringParserUtil.parseNullToEmpty(id);
        background = StringParserUtil.parseNullToEmpty(background);
        target = StringParserUtil.parseNullToEmpty(target);
        expectation = StringParserUtil.parseNullToEmpty(expectation);
    }

    public static GetProjectProposal toProjectProposalDto(Project project) {
        return GetProjectProposal.builder()
                .id(project.getId().toHexString())
                .background(project.getProposal().getBackground())
                .introduce(project.getProposal().getIntroduce())
                .target(project.getProposal().getTarget())
                .expectation(project.getProposal().getExpectation())
                .build();
    }
}
