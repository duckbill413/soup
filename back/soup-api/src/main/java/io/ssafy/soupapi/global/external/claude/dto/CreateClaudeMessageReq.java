package io.ssafy.soupapi.global.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record CreateClaudeMessageReq(
    String model,
    @JsonProperty("max_tokens") Integer maxTokens,
    String system,
    List<MessageDto> messages
) {

    @Builder
    public CreateClaudeMessageReq {
        if (Objects.isNull(model)) {
            model = "claude-3-opus-20240229";
        }
        if (Objects.isNull(maxTokens)) {
            maxTokens = 1024;
        }
        if (Objects.isNull(system)) {
            system = "Flesh out an idea for an IT service development project. Answer four things each in a bullet point. 기획 배경, 서비스 소개, 서비스 타겟, 기대 효과. Write ONLY CONTENTS after the bullet point. No titles. Answer each in one Korean sentence.";
        }
        if (Objects.isNull(messages)) {
            messages = new ArrayList<>();
        }
    }

    public void addMessage(MessageDto messageDto) {
        this.messages.add(messageDto);
    }

}