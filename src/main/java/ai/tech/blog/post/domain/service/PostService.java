package ai.tech.blog.post.domain.service;

import ai.tech.blog.post.application.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    Optional<PostDto> createPost(final PostDto postDto);

    Page<PostDto> findAll(final Pageable pageable);

    Optional<PostDto> findById(final Long id);

    Optional<PostDto> updatePost(final Long id, final PostDto postDto);

    void deletePostById(final Long id);

    void deleteAll();
}