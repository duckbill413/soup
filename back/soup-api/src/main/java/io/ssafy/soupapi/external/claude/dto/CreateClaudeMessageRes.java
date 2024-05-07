package io.ssafy.soupapi.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.StringTokenizer;

@ToString
@Getter @Setter
public class CreateClaudeMessageRes {
    String id;
    String type;
    String role;

    String model;
    @JsonProperty("stop_sequence")
    String stopSequence;
    Usage usage;
    List<Content> content;
    @JsonProperty("stop_reason")
    String stopReason;

    @ToString
    @Getter @Setter
    static class Content {
        String text;
        String type;
    }

    @ToString
    @Getter @Setter
    static class Usage {
        @JsonProperty("input_tokens")
        Integer inputTokens;
        @JsonProperty("output_tokens")
        Integer outputTokens;
    }

    public CreateAiProposal toResponse() {
        String claudeRes = this.content.get(0).getText();
        StringTokenizer st = new StringTokenizer(claudeRes, "\n");

        String background = st.nextToken().substring(2);
        String intro = st.nextToken().substring(2);
        String target = st.nextToken().substring(2);
        String result = st.nextToken().substring(2);

        return CreateAiProposal.builder()
                .background(List.of(background))
                .intro(intro)
                .target(target)
                .result(result)
                .build();
    }
}