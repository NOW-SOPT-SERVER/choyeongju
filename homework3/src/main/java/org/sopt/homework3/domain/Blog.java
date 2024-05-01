package org.sopt.homework3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.homework3.service.dto.BlogCreateRequest;
import org.sopt.homework3.service.dto.BlogTitleUpdateRequest;
import org.sopt.homework3.service.dto.BlogWriteRequest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Blog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(length = 200)
    private String title;

    private String description;

    private Blog(Member member, String title, String description) {
        this.member = member;
        this.title = title;
        this.description = description;
    }

    public static Blog create(Member member, BlogCreateRequest blogCreateRequest) {
        return new Blog(member, blogCreateRequest.title(), blogCreateRequest.description());
    }

    public void updateTitle(BlogTitleUpdateRequest blogTitleUpdateRequest) {
        this.title = blogTitleUpdateRequest.title();
    }

    //여기서부터 숙제코드
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Member member, BlogWriteRequest blogWriteRequest) {
        Post newPost = new Post(this, blogWriteRequest.title(), blogWriteRequest.contents(), member);
        this.posts.add(newPost);
    }
}