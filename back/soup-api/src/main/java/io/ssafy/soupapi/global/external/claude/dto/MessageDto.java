package io.ssafy.soupapi.global.external.claude.dto;

import lombok.Builder;

@Builder
public record MessageDto (
        String role,
        String content
) {

    public static class MessageDtoBuilder {
        private String role = "user";
    }

    public CreateClaudeMessageReq toCreateClaudeMessageReq() {
        CreateClaudeMessageReq claudeReq = CreateClaudeMessageReq.builder().build();
        claudeReq.addMessage(this);
        return claudeReq;
    }

}
