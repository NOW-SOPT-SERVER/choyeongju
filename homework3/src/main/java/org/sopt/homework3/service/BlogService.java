package org.sopt.homework3.service;


import org.sopt.homework3.exception.CustomizedException;
import org.sopt.homework3.service.dto.BlogPostGetRequest;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.sopt.homework3.common.dto.ErrorMessage;
import org.sopt.homework3.domain.Blog;
import org.sopt.homework3.domain.Member;
import org.sopt.homework3.domain.Post;
import org.sopt.homework3.exception.NotFoundException;
import org.sopt.homework3.repository.BlogRepository;
import org.sopt.homework3.service.dto.BlogCreateRequest;
import org.sopt.homework3.service.dto.BlogTitleUpdateRequest;
import org.sopt.homework3.service.dto.BlogWriteRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberService memberService;


    public String create(Long memberId, BlogCreateRequest blogCreateRequest) {
        Member member = memberService.findById(memberId);
        Blog blog = blogRepository.save(Blog.create(member, blogCreateRequest));
        return blog.getId().toString();
    }

    private Blog findById(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND)
        );
    }

    @Transactional
    public void updateTitle(Long blogId, BlogTitleUpdateRequest blogTitleUpdateRequest) {
        Blog blog = findById(blogId);
        blog.updateTitle(blogTitleUpdateRequest);
    }

    //여기까지가 세미나 코드

    @Transactional
    public void writePost(Long blogId,Long memberId,BlogWriteRequest blogWriteRequest) {
        Blog blog = findById(blogId);
        Member author = memberService.findById(memberId);
        blog.addPost(author, blogWriteRequest);
        blogRepository.save(blog);
    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = findById(blogId);
        blogRepository.delete(blog);
    }

    public Blog findBlogById(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BLOG_NOT_FOUND)
        );
    }

    public void validateMember(Long blogId, Long memberId) { //블로그 소유권 확인
        Blog blog = findBlogById(blogId);
        if (!blog.getMember().getId().equals(memberId)) { //예외처리
            throw new CustomizedException(ErrorMessage.NON_MEMBER);
        }
    }

      /*
    @Transactional(readOnly = true)
    public BlogPostGetRequest getPostsByBlogId(final Long blogId) {
        return BlogPostGetRequest.of(blogRepository.findById(blogId).orElseThrow(
                ()->new NotFoundException(ErrorMessage.BLOG_NOT_FOUND)
        );
        }
    }
     */

    //GET API 위한
    public Blog getPostById(Long blogId, Long memberId) {
        //블로그 소유권 확인
        validateMember(blogId, memberId);
        return blogRepository.findById(blogId)
                .orElseThrow(
                        () -> new CustomizedException(ErrorMessage.POST_NOT_FOUND));
    }
}

