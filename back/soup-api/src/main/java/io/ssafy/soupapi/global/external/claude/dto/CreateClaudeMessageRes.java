package io.ssafy.soupapi.global.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import lombok.Builder;

import java.util.List;
import java.util.StringTokenizer;

@Builder
public record CreateClaudeMessageRes(
    String id,
    String type,
    String role,

    String model,
    @JsonProperty("stop_sequence") String stopSequence,
    Usage usage,
    List<Content> content,
    @JsonProperty("stop_reason") String stopReason
){

    public record Content(
        String text,
        String type
    ) {
    }

    public record Usage(
        @JsonProperty("input_tokens") Integer inputTokens,
        @JsonProperty("output_tokens") Integer outputTokens
    ) {
    }

    public CreateAiProposal toResponse() {
        String claudeRes = this.content.get(0).text;
        StringTokenizer st = new StringTokenizer(claudeRes, "\n");

        String background = st.nextToken().substring(2);
        String intro = st.nextToken().substring(2);
        String target = st.nextToken().substring(2);
        String result = st.nextToken().substring(2);

        return CreateAiProposal.builder()
                .background(List.of(background))
                .intro(List.of(intro))
                .target(List.of(target))
                .result(List.of(result))
                .build();
    }
}