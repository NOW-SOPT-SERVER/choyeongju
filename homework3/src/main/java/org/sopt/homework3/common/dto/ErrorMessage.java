package org.sopt.homework3.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ID에 해당하는 사용자가 존재하지 않습니다."),
    BLOG_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ID에 해당하는 블로그가 없습니다."),
    NON_MEMBER(HttpStatus.NOT_EXTENDED.value(), "블로그 소유자가 아니므로 권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_EXTENDED.value(), "아이디에 해당하는 블로그의 포스트가 없습니다.")
    ;
    private final int status;
    private final String message;
}