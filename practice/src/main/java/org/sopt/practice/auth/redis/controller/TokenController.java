package org.sopt.practice.auth.redis.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.auth.PrincipalHandler;
import org.sopt.practice.auth.redis.service.RedisTokenService;
import org.sopt.practice.service.dto.AccessTokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class TokenController {

    private final RedisTokenService redisTokenService;
    private final PrincipalHandler principalHandler;

    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenDto> refreshToken(){
        Long userId = principalHandler.getUserIdFromPrincipal();
        AccessTokenDto newAccessTokenResponse = redisTokenService.refreshToken(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newAccessTokenResponse);
    }
}
