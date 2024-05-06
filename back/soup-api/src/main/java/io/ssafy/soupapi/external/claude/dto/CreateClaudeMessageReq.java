package io.ssafy.soupapi.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter @Setter
public class CreateClaudeMessageReq {
    String model = "claude-3-opus-20240229";
    @JsonProperty("max_tokens")
    Integer maxTokens = 1024;
    String system = "Flesh out an idea for an IT service development project.Answer four things each in a bullet point. 기획 배경, 서비스 소개, 서비스 타겟, 기대 효과. Write ONLY CONTENTS after the bullet point. No titles. Answer each in one Korean sentence.";
    List<MessageDto> messages = new ArrayList<>();

    public CreateClaudeMessageReq(CreateAiProposal req) {
        MessageDto messageDto = new MessageDto(req);
        messages.add(messageDto);
    }
}
