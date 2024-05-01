package org.sopt.homework3.service.dto;

import org.sopt.homework3.domain.Post;

public record BlogPostGetRequest(
        String title,
        String content
) {
    public static BlogPostGetRequest of(Post post) {
        return new BlogPostGetRequest(post.getTitle(), post.getContent());
    }
}
