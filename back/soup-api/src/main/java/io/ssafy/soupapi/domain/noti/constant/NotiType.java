package io.ssafy.soupapi.domain.noti.constant;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum NotiType {

    SSE("sse"),
    MENTION("mention"),
    READ_NOTI("read-noti")
    ;

    private final String eventName; // FE에서 수신할 때 SSE type

    NotiType(String eventName) {
        this.eventName = eventName;
    }
}
