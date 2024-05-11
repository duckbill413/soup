package io.ssafy.soupapi.domain.chat.api;

import io.ssafy.soupapi.domain.chat.application.ChatService;
import io.ssafy.soupapi.domain.chat.application.ChatroomService;
import io.ssafy.soupapi.domain.chat.dto.request.ChatMessageReq;
import io.ssafy.soupapi.domain.chat.dto.response.ChatMessageRes;
import io.ssafy.soupapi.domain.chat.dto.response.GetChatMessageRes;
import io.ssafy.soupapi.domain.chat.redis.RedisPublisher;
import io.ssafy.soupapi.global.common.code.SuccessCode;
import io.ssafy.soupapi.global.common.request.PageOffsetRequest;
import io.ssafy.soupapi.global.common.response.BaseResponse;
import io.ssafy.soupapi.global.security.user.UserSecurityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@Tag(name = "채팅", description = "채팅 관련")
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatService chatService;
    private final ChatroomService chatroomService;

    @MessageMapping("/chatrooms/{chatroomId}")
    public void chat(@DestinationVariable String chatroomId, ChatMessageReq chatMessageReq) {
        ChannelTopic topic = chatroomService.enterChatroom(chatroomId); // topic 입장, 대화를 위해 (안 돼있다면) 리스너와 연동
        ChatMessageRes chatMessageRes = chatService.saveMessage(chatroomId, chatMessageReq); // (DB &) Redis에 대화 저장
        redisPublisher.publish(topic, chatMessageRes); // WebSocket에 발행된 메시지를 redis로 발행
    }

    @Operation(summary = "채팅방의 메시지 내역 조회", description = "특정 채팅방에 채팅 내역을 조회한다.")
//    @PreAuthorize("@authService.hasProjectRoleMember(#chatroomId, #userSecurityDTO.getId())")
    @GetMapping("/api/chatrooms/{chatroomId}")
    public ResponseEntity<BaseResponse<List<GetChatMessageRes>>> getChatMessages(
            @PathVariable String chatroomId,
            @Valid PageOffsetRequest pageOffset,
            @RequestParam @Parameter(description = "페이징 요청 시 기준 시간.\n\n" +
                    "채팅 시 DB가 자꾸 바뀌기 때문에 index를 기반으로 조회를 하기가 어렵습니다.\n\n" +
                    "이 시간을 기준으로 페이지네이션을 하므로 이 값은 페이징을 요청하는 동안 **일정해야** 합니다.\n\n" +
                    "형식은 yyyy-MM-ddTHH:mm:ss 으로 한국 시간 입니다.") LocalDateTime standardTime,
            @AuthenticationPrincipal UserSecurityDTO userSecurityDTO
    ) {
        return BaseResponse.success(
                SuccessCode.SELECT_SUCCESS,
                chatService.getChatMessages(chatroomId, pageOffset, standardTime)
        );
    }
}
