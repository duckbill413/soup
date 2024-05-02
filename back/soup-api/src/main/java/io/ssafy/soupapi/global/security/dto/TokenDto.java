package io.ssafy.soupapi.global.security.dto;

public record TokenDto (
    String accessToken,
    String refreshToken
) {
}
