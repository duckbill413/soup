package io.ssafy.soupapi.domain.project.usecase.dto;

import io.ssafy.soupapi.global.external.claude.dto.MessageDto;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateAiProposal(
    List<String> background,
    List<String> intro,
    List<String> target,
    List<String> result
) {

    public MessageDto toMessageDto() {
        StringBuilder sb = new StringBuilder();

        // 기획 배경
        sb.append("I need an idea for my IT service development project. I want to start from ");
        sb = appendKeywords(sb, this.background);

        // 서비스 소개
        sb.append("I want my service related to ");
        sb = appendKeywords(sb, this.intro);

        // 서비스 타겟
        sb.append("I want to target ");
        sb = appendKeywords(sb, this.target);

        // 기대 효과
        sb.append("I hope users to ");
        sb = appendKeywords(sb, this.result);

        return MessageDto.builder().content(sb.toString()).build();
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
