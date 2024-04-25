package io.ssafy.soupapi.domain.connect;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connect")
@Tag(name = "연결 테스트 API", description = "네트워크 연결 테스트시 사용")
public class ConnectController {
    @GetMapping
    @Operation(summary = "연결 테스트", description = "연결 테스트용")
    public ResponseEntity<String> connectTest(){
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 연결되었습니다.");
    }
}

