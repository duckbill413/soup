package io.ssafy.soupapi.domain.jira.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.soupapi.domain.jira.dao.JiraRepository;
import io.ssafy.soupapi.domain.jira.dto.JiraUserDatum;
import io.ssafy.soupapi.domain.project.mongodb.dao.MProjectRepository;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JiraServiceImpl implements JiraService {
    private final JiraRepository jiraRepository;
    private final MProjectRepository mProjectRepository;

    @Override
    public List<JiraUserDatum> findJiraTeamInfo(String projectId) {
        var jiraInfo = mProjectRepository.findProjectJiraInfo(new ObjectId(projectId)).orElseThrow(() ->
                new BaseExceptionHandler(ErrorCode.NOT_FOUND_PROJECT)).getInfo();

        try {
            return jiraRepository.findJiraTeamInfo(jiraInfo);
        } catch (JsonProcessingException e) {
            throw new BaseExceptionHandler(ErrorCode.JSON_PARSE_ERROR);
        }
    }
}
