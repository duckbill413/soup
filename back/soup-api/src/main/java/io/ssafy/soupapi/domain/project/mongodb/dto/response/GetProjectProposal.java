package io.ssafy.soupapi.domain.project.mongodb.dto.response;

import io.ssafy.soupapi.domain.project.mongodb.entity.Proposal;
import io.ssafy.soupapi.global.util.StringParserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.bson.types.ObjectId;

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
        introduce = StringParserUtil.parseNullToEmpty(introduce);
    }

    public static GetProjectProposal toProjectProposalDto(ObjectId projectId, Proposal proposal) {
        return GetProjectProposal.builder()
                .id(projectId.toHexString())
                .background(proposal.getBackground())
                .introduce(proposal.getIntroduce())
                .target(proposal.getTarget())
                .expectation(proposal.getExpectation())
                .build();
    }
}
