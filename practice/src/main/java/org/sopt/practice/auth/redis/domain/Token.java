package org.sopt.practice.auth.redis.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "", timeToLive = 60 * 60 * 24 * 1000L * 14) //TTL 설정
//value = ""이렇게 하면, 객체가 Redis에 저장될 때 클래스의 전체 이름이 해시 키로 사용

@AllArgsConstructor
@Getter
@Builder
public class Token {

    @Id
    private Long id;

    @Indexed //Redis에서 Indexed 어노테이션 사용 시 이 값으로 객체 값 찾을 수 있다. 주로 검색 조건으로 사용되는 필드에 적용!
    private String refreshToken;

    public static Token of(
            final Long id,
            final String refreshToken
    ){
        return Token.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build();
    }
}
