package io.ssafy.soupapi.domain.chat.api;

import io.ssafy.soupapi.domain.chat.application.ChatService;
import io.ssafy.soupapi.domain.chat.application.ChatroomService;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageDto;
import io.ssafy.soupapi.domain.chat.redis.RedisPublisher;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@Tag(name = "채팅", description = "채팅 관련")
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatService chatService;
    private final ChatroomService chatroomService;

    @MessageMapping("/chatrooms/{chatroomId}")
    public void chat(@DestinationVariable String chatroomId, ChatMessageDto chatMessageDto) {
        ChannelTopic topic = chatroomService.enterChatroom(chatroomId); // topic 입장, 대화를 위해 (안 돼있다면) 리스너와 연동
        redisPublisher.publish(topic, chatMessageDto); // WebSocket에 발행된 메시지를 redis로 발행
        chatService.saveMessage(chatroomId, chatMessageDto); // (DB &) Redis에 대화 저장
    }
}
