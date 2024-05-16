package io.ssafy.soupapi.global.util;

import io.ssafy.soupapi.domain.member.dao.MemberRepository;
import io.ssafy.soupapi.domain.member.entity.Member;
import io.ssafy.soupapi.domain.project.postgresql.dao.PProjectRepository;
import io.ssafy.soupapi.domain.project.postgresql.entity.Project;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * DB에서 domain entity 조회 후 에러 처리하는 함수 모음
 */
@Component
@RequiredArgsConstructor
public class FindEntityUtil {

    /**
     * domain entity 별 repository
     */

    private final MemberRepository memberRepository;
    private final PProjectRepository projectRepository;

    /**
     * findById 유틸 함수 모음
     */

    @Transactional(readOnly = true)
    public Member findMemberById(UUID memberId) {
        return memberRepository.findById(memberId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_USER)
        );
    }

    @Transactional(readOnly = true)
    public Map<String, Member> findAllMemberByIdAndGenerateMap(List<UUID> memberIdList) {
        List<Member> senderList = memberRepository.findAllById(memberIdList);
        Map<String, Member> memberMap = new HashMap<>();
        for (Member m : senderList) {
            memberMap.put(String.valueOf(m.getId()), m);
        }
        return memberMap;
    }

    @Transactional(readOnly = true)
    public Project findPProjectById(String projectId) {
        return projectRepository.findById(projectId).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT)
        );
    }

    @Transactional(readOnly = true)
    public Map<String, String> findAllProjectByIdAndGenerateMap(List<String> projectIdList) {
        List<Project> projectList = projectRepository.findAllById(projectIdList);
        Map<String, String> projectNameMap = new HashMap<>();
        for (Project p : projectList) {
            projectNameMap.put(p.getId(), p.getName());
        }
        return projectNameMap;
    }

}
