package org.sopt.practice.service.dto;

// 회원가입 시 Return 해주기 위한 DTO 클래스
public record UserJoinResponse(
        String accessToken,
        String refreshToken,
        String userId
) {

    public static UserJoinResponse of(
            String accessToken,
            String refreshToken,
            String userId
    ) {
        return new UserJoinResponse(accessToken, refreshToken, userId);
    }
}

