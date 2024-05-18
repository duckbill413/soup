package io.ssafy.soupapi.domain.noti.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class SseNotiRes {

    int unreadNotiNum;
    Object newlyAddedNoti;

}
