package io.ssafy.soupapi.domain.project.mongodb.dto.liveblock;

import io.ssafy.soupapi.domain.project.mongodb.entity.Proposal;

public record LiveProposal(
        LiveProposalAfter after,
        LiveProposalBefore before
) {
    public static Proposal toProposal(LiveProposal liveProposal) {
        if (liveProposal == null || liveProposal.after == null) {
            return Proposal.builder().build();
        }
        return Proposal.builder()
                .background(liveProposal.after().background())
                .introduce(liveProposal.after().intro())
                .target(liveProposal.after().target())
                .expectation(liveProposal.after().effect())
                .build();
    }
}
