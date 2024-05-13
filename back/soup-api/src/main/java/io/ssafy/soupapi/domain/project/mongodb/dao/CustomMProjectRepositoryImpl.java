package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CustomMProjectRepositoryImpl implements CustomMProjectRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * projectId 프로젝트의 채팅 메시지 중에서, afterTimestamp 이후에 만들어진 size1 개의 채팅 메시지를 반환한다.
     */
    @Override
    public List<ChatMessage> getNChatMessagesBefore(String projectId, Instant beforeTime, int size) {

        AggregationOperation opMatchProjectId = Aggregation.stage("""
            { $match: { _id: ObjectId('#projectId#') } }
        """.replace("#projectId#", projectId));

        AggregationOperation opUnwind = Aggregation.stage("""
            { $unwind: "$project_chats" }
        """);

        AggregationOperation opFilterByTimeAfter = Aggregation.stage("""
            { $match: { "project_chats.chat_message_timestamp": { $lt: "#beforeTime#" } } }
        """.replace("#beforeTime#", String.valueOf(beforeTime)));

        AggregationOperation opSortDesc = Aggregation.stage("""
            { $sort: { "project_chats.chat_message_timestamp": -1 } }
        """);

        AggregationOperation opLimit = Aggregation.stage("""
            { $limit: #size# }
        """.replace("#size#", String.valueOf(size)));

        AggregationOperation opRelation = Aggregation.stage("""
            {
                 $project: {
                     _id: 0,
                     chat_message_id: "$project_chats.chat_message_id",
                     chat_message_sender_id: "$project_chats.chat_message_sender_id",
                     chat_message_content: "$project_chats.chat_message_content",
                     chat_message_timestamp: "$project_chats.chat_message_timestamp"
                 }
             }
        """);

        Aggregation aggregation;
        if (beforeTime == null) {
            aggregation = Aggregation.newAggregation(
                    opMatchProjectId, opUnwind, opSortDesc, opLimit, opRelation
            );
        } else {
            aggregation = Aggregation.newAggregation(
                    opMatchProjectId, opUnwind, opFilterByTimeAfter, opSortDesc, opLimit, opRelation
            );
        }
        AggregationResults<ChatMessage> chatMessageList = mongoTemplate.aggregate(aggregation, "projects", ChatMessage.class);
        return chatMessageList.getMappedResults();
    }
}
