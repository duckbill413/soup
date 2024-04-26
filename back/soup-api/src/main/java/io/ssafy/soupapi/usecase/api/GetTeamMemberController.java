package io.ssafy.soupapi.usecase.api;


import io.ssafy.soupapi.usecase.application.GetTeamMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "프로젝트", description = "프로젝트 팀원 조회 Usecase")
public class GetTeamMemberController {
    private final GetTeamMemberService getTeamMemberService;


}
