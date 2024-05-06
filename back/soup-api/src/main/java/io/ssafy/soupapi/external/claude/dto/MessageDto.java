package io.ssafy.soupapi.external.claude.dto;

import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MessageDto {
    String role = "user";
    String content;

    public MessageDto(CreateAiProposal req) {
        StringBuilder sb = new StringBuilder();
        sb.append("I need an idea for my IT service development project. I want to start from ");
        for (String keyword : req.background()) {
            sb.append("'").append(keyword).append("', ");
        }
        sb.replace(sb.length() - 2, sb.length() - 1, ".");
        sb.append("I want my service related to '").append(req.intro()).append("'. ");
        sb.append("I want to target '").append(req.target()).append("'. ");
        sb.append("I hope users to '").append(req.result()).append("'.");

        this.content = sb.toString();
    }
}
