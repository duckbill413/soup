package io.ssafy.soupapi.domain.project.usecase.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateAiProposal(
    List<String> background,
    String intro,
    String target,
    String result
) {
}
