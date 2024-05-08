package io.ssafy.soupapi.global.external.liveblocks.application;

import io.ssafy.soupapi.domain.project.constant.StepName;
import io.ssafy.soupapi.global.external.liveblocks.LiveblocksFeignClient;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.CreateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.request.UpdateRoomReq;
import io.ssafy.soupapi.global.external.liveblocks.dto.response.ChangeRoomRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class LiveblocksServiceImpl implements LiveblocksService {

    private final LiveblocksFeignClient liveblocksFeignClient;

    @Override
    public void createAllStepRooms(String memberId, String projectId) {
        String roomIdBase = "/project/" + projectId + "/"; // roomId: /project/{projectID}/

        for (StepName stepName : StepName.values()) {
            CreateRoomReq createRoomReq = CreateRoomReq.builder()
                    .id(roomIdBase + stepName) // roomId: /project/{projectID}/outline
                    .build();
            createRoomReq.usersAccesses().put(memberId, List.of("room:write"));

            ChangeRoomRes changeRoomRes = liveblocksFeignClient.createRoom(createRoomReq);

            // TODO: 성공 실패 처리하면 될 듯
        }
    }

    @Override
    public void addMemberToAllStepRooms(String memberId, String projectId) {
        String roomId = "%2Fproject%2F" + projectId + "%2F"; // pathVariable로 사용하므로 '/'를 %2F로 변환 필요

        for (StepName stepName : StepName.values()) {
            UpdateRoomReq updateRoomReq = UpdateRoomReq.builder().build();
            updateRoomReq.usersAccesses().put(memberId, List.of("room:write"));

            ChangeRoomRes changeRoomRes = liveblocksFeignClient.updateRoom(roomId + stepName, updateRoomReq);

            // TODO: 성공 실패 처리하면 될 듯
        }
    }

}
