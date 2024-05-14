package io.ssafy.soupapi.domain.noti.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class GetNotiRes {
    @Builder.Default
    List<NewNotiRes> notiList = new ArrayList<>();
}
