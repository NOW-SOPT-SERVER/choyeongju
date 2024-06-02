package org.sopt.practice.auth.redis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.redis.domain.Token;
import org.sopt.practice.auth.redis.repository.RedisTokenRepository;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.common.jwt.JwtValidationType;
import org.sopt.practice.exception.UnauthorizedException;
import org.sopt.practice.service.dto.AccessTokenDto;
import org.sopt.practice.auth.UserAuthentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final RedisTokenRepository redisTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AccessTokenDto refreshToken(Long userId) {
        // Redis에서 Refresh Token을 조회
        Token token = redisTokenRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND));

        // Refresh Token 검증
        JwtValidationType validationType = jwtTokenProvider.validateToken(token.getRefreshToken());
        if (validationType == JwtValidationType.EXPIRED_JWT_TOKEN) {
            throw new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        } else if (validationType != JwtValidationType.VALID_JWT) {
            throw new UnauthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }

        // 새로운 Access Token 발급
        String newAccessToken = jwtTokenProvider.newAccessToken(token.getRefreshToken());

        // 새로운 Access Token을 포함한 응답 객체 반환
        return AccessTokenDto.of(newAccessToken);
    }
}
