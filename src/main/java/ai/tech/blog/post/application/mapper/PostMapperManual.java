package ai.tech.blog.post.application.mapper;

import ai.tech.blog.post.application.dto.PostDto;
import ai.tech.blog.post.domain.model.Post;
import ai.tech.blog.user.domain.service.AuthService;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;

public class PostMapperManual {
    private final AuthService authService;

    public PostMapperManual(final AuthService authService) {
        this.authService = authService;
    }

    public User getSignedInUser() {
        return authService.getSignedInUser().orElseThrow(
                () -> new IllegalArgumentException("No users is signed in.")
        );
    }

    public PostDto toDto(final Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(Instant.now())
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUsername())
                .build();
    }

    public Post toEntity(final PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .createdAt(postDto.getCreatedAt())
                .updatedAt(Instant.now())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .username(getSignedInUser().getUsername())
                .build();
    }
}