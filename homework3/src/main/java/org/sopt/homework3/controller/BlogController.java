package org.sopt.homework3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.homework3.common.dto.ErrorMessage;
import org.sopt.homework3.common.dto.SuccessMessage;
import org.sopt.homework3.common.dto.SuccessStatusResponse;
import org.sopt.homework3.domain.Blog;
import org.sopt.homework3.domain.Post;
import org.sopt.homework3.exception.CustomizedException;
import org.sopt.homework3.exception.NotFoundException;
import org.sopt.homework3.repository.BlogRepository;
import org.sopt.homework3.service.BlogService;
import org.sopt.homework3.service.dto.BlogCreateRequest;
import org.sopt.homework3.service.dto.BlogPostGetRequest;
import org.sopt.homework3.service.dto.BlogTitleUpdateRequest;
import org.sopt.homework3.service.dto.BlogWriteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final BlogRepository blogRepository;

    @PostMapping("/blog")
    public ResponseEntity<SuccessStatusResponse> createBlog(
            @RequestHeader(name = "memberId") Long memberId,
            @RequestBody BlogCreateRequest blogCreateRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).header(
                        "Location",
                        blogService.create(memberId, blogCreateRequest))
                .body(SuccessStatusResponse.of(SuccessMessage.BLOG_CREATE_SUCCESS));
    }

    @PatchMapping("/blog/{blogId}/title")
    public ResponseEntity updateBlogTitle(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogTitleUpdateRequest blogTitleUpdateRequest
    ) {
        blogService.updateTitle(blogId, blogTitleUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    //여기서부터 숙제 코드
    @PostMapping("/blog/{blogId}/post") //글쓰기
    public ResponseEntity<SuccessStatusResponse> writeBlogPost(
            @PathVariable Long blogId,
            @RequestHeader(name = "memberId") Long memberId,
            @Valid @RequestBody BlogWriteRequest blogWriteRequest
    ) {
        blogService.validateMember(blogId, memberId); //블로그 소유자인지 확인
        blogService.writePost(blogId, memberId, blogWriteRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessStatusResponse.of(SuccessMessage.BLOG_POST_CREATE_SUCCESS));
    }

    @DeleteMapping("/blog/{blogId}") //블로그 삭제
    public ResponseEntity<Void> deleteBlog(@PathVariable Long blogId) {
        blogService.deleteBlog(blogId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<BlogPostGetRequest>> findPostsByBlogId(
            @PathVariable Long blogId,
            @RequestHeader(name = "memberId") Long memberId
    ){
        Blog blog = blogService.getPostById(blogId, blogId);
        List<BlogPostGetRequest> blogPostGetRequests = blog.getPosts().stream()
                .map(BlogPostGetRequest::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(blogPostGetRequests);
    }
}
