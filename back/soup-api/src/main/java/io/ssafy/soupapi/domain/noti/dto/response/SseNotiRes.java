package io.ssafy.soupapi.domain.noti.dto.response;

import io.ssafy.soupapi.domain.noti.entity.MNoti;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class SseNotiRes {

    int unreadNotiNum;
    NewNotiRes newlyAddedNoti;

}
