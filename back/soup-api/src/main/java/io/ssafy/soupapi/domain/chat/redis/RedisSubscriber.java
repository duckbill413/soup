package io.ssafy.soupapi.domain.chat.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplateJackson;
    private final SimpMessageSendingOperations messagingTemplate;

    // Redis 에서 메시지가 발행(publish)되면, listener 가 해당 메시지를 읽어서 처리
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("RedisSubscriber 안에 onMessage()에 왔어요!");
            String topicName = new String(pattern); // topicName 이자 roomId
            String publishedMsg = (String) redisTemplateJackson.getStringSerializer().deserialize(message.getBody());
            ChatMessageRes msg = objectMapper.readValue(publishedMsg, ChatMessageRes.class);
            messagingTemplate.convertAndSend("/sub/chatrooms/" + topicName, msg);
        } catch (JsonProcessingException e) {
            log.info("redis에서 publish 된 메시지 역직렬화 중 에러 발생");
            throw new RuntimeException(e);
        }
    }

}
