package io.ssafy.soupapi.domain.chat.redis;

import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.global.util.DateConverterUtil;
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

    private final RedisTemplate<String, ChatMessageRes> redisTemplateChatMessageRes;
    private final SimpMessageSendingOperations messagingTemplate;

    // Redis 에서 채팅 메시지가 발행(publish)되면, listener 가 해당 메시지를 읽어서 처리
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String topicName = new String(pattern); // topicName == roomId == projectId
        ChatMessageRes msg = (ChatMessageRes) redisTemplateChatMessageRes.getValueSerializer().deserialize(message.getBody());
        if (msg != null) msg.setSentAt(DateConverterUtil.utcZdtToKstZdt(msg.getSentAt()));
        messagingTemplate.convertAndSend("/sub/chatrooms/" + topicName, msg);
    }

}
