package io.ssafy.soupapi.domain.project.postgresql.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로젝트")
@Log4j2
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
}
