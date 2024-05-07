package io.ssafy.soupapi.external.claude.dto;

import io.ssafy.soupapi.domain.project.usecase.dto.CreateAiProposal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class MessageDto {
    String role = "user";
    String content;

    public MessageDto(CreateAiProposal req) {
        StringBuilder sb = new StringBuilder();

        // 기획 배경
        sb.append("I need an idea for my IT service development project. I want to start from ");
        sb = appendKeywords(sb, req.background());

        // 서비스 소개
        sb.append("I want my service related to ");
        sb = appendKeywords(sb, req.intro());

        // 서비스 타겟
        sb.append("I want to target ");
        sb = appendKeywords(sb, req.target());

        // 기대 효과
        sb.append("I hope users to ");
        sb = appendKeywords(sb, req.result());

        this.content = sb.toString();
    }

    // {'a', 'b'. } 형태로 append 된다.
    private StringBuilder appendKeywords(StringBuilder sb, List<String> keywordList) {
        for (String keyword : keywordList) {
            sb.append("'").append(keyword).append("', ");
        }
        sb.replace(sb.length() - 2, sb.length() - 1, ".");
        return sb;
    }
}
