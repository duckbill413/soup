package io.ssafy.soupapi.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record CreateClaudeMessageReq(
    String model,
    @JsonProperty("max_tokens") Integer maxTokens,
    String system,
    List<MessageDto> messages
) {

    public static class CreateClaudeMessageReqBuilder {
        private String model = "claude-3-opus-20240229";
        private Integer maxTokens = 1024;
        private String system = "Flesh out an idea for an IT service development project. Answer four things each in a bullet point. 기획 배경, 서비스 소개, 서비스 타겟, 기대 효과. Write ONLY CONTENTS after the bullet point. No titles. Answer each in one Korean sentence.";
        private List<MessageDto> messages = new ArrayList<>();
    }

    public void addMessage(MessageDto messageDto) {
        this.messages.add(messageDto);
    }

}