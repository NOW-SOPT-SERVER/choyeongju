package org.sopt.homework3.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage{

    BLOG_CREATE_SUCCESS(HttpStatus.CREATED.value(), "블로그 생성이 완료되었습니다."),
    BLOG_POST_CREATE_SUCCESS(HttpStatus.CREATED.value(), "블로그 글이 작성되었습니다."),
    BLOG_POST_GET_SUCCESS(HttpStatus.CREATED.value(),"블로그 글을 성공적으로 가져왔습니다."),
    BLOG_POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"블로그 글을 찾을 수 없습니다.")

    ;
    private final int status;
    private final String message;
}