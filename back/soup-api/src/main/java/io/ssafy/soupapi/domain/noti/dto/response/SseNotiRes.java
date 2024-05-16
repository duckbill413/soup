package io.ssafy.soupapi.domain.noti.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SseNotiRes {

    int unreadNotiNum;
    NewNotiRes newlyAddedNoti;

}
