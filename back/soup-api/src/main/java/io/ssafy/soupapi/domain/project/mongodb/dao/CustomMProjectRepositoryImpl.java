package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CustomMProjectRepositoryImpl implements CustomMProjectRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * projectId 프로젝트의 채팅 메시지 중에서, afterTimestamp 이후에 만들어진 size1 개의 채팅 메시지를 반환한다.
     */
    @Override
    public List<ChatMessage> getNChatMessagesAfter(String projectId, LocalDateTime afterTimestamp, int size) {

        AggregationOperation operation1 = Aggregation.stage("""
            { $match: { _id: ObjectId('#projectId#') } }
        """.replace("#projectId#", projectId));

        AggregationOperation operation2 = Aggregation.stage("""
            {
                $project: {
                    _id: 0,
                    project_chats: {
                        $filter: {
                            input: "$project_chats",
                            as: "chat",
                            cond: { $gt: [ "$$chat.chat_message_timestamp", "#afterTimestamp#" ] }
                        }
                    }
                }
            }
        """.replace("#afterTimestamp#", String.valueOf(afterTimestamp)));

        AggregationOperation operation3 = Aggregation.stage("""
            {
                $project: {
                    project_chats: {
                        $slice: ["$project_chats", 0, #size#]
                    }
                }
            }
        """.replace("#size#", String.valueOf(size)));

        Aggregation aggregation = Aggregation.newAggregation(operation1, operation2, operation3);
        AggregationResults<Project> project = mongoTemplate.aggregate(aggregation, "projects", Project.class);
        List<Project> projectList = project.getMappedResults();
        return projectList.get(0).getChats();
    }
}
