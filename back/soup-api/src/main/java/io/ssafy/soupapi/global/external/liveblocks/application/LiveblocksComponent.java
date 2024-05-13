package io.ssafy.soupapi.global.external.liveblocks.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.soupapi.domain.project.constant.StepName;
import io.ssafy.soupapi.global.external.liveblocks.dao.LbFeignClient;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.CreateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.UpdateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.ChangeRoomRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * service + repository의 로직이 함께 있단 점을 고려해 service/repository가 아닌 componenet로 등록
 */

@Log4j2
@Component
@RequiredArgsConstructor
public class LiveblocksComponent {

    private final LbFeignClient lbFeignClient;
    private final ObjectMapper objectMapper;

    public void createAllStepRooms(String memberId, String projectId) {
        String roomIdBase = "/project/" + projectId + "/"; // roomId: /project/{projectID}/

        for (StepName stepName : StepName.values()) {
            CreateRoomReq createRoomReq = CreateRoomReq.builder()
                    .id(roomIdBase + stepName) // roomId: /project/{projectID}/outline
                    .build();
            createRoomReq.usersAccesses().put(memberId, List.of("room:write"));

            ChangeRoomRes changeRoomRes = lbFeignClient.createRoom(createRoomReq);

            // TODO: 성공 실패 처리하면 될 듯
        }
    }

    public void addMemberToAllStepRooms(String memberId, String projectId) {
        for (StepName stepName : StepName.values()) {
            UpdateRoomReq updateRoomReq = UpdateRoomReq.builder().build();
            updateRoomReq.usersAccesses().put(memberId, List.of("room:write"));

            ChangeRoomRes changeRoomRes = lbFeignClient.updateRoom(
                projectId, stepName.name(), updateRoomReq
            );

            // TODO: 성공 실패 처리하면 될 듯
        }
    }

    public <T> T getRoomStorageDocument(String projectId, StepName stepName, Class<T> clazz) {
        HashMap<String, Object> map = lbFeignClient.getRoomStorageDocument(projectId, stepName.name());
        Object object = map.get(stepName.name());
        T result = objectMapper.convertValue(object, clazz);
//        log.info("result는.. {}", result);
        return result;
    }

}
