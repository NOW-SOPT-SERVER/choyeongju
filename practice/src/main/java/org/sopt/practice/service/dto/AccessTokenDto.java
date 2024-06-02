package org.sopt.practice.service.dto;

// Access Token을 반환하기 위한 DTO 클래스
public record AccessTokenDto(
        String accessToken
) {
    public static AccessTokenDto of(String accessToken) {
        return new AccessTokenDto(accessToken);
    }
}
