package ai.tech.blog.post.domain.service;

import ai.tech.blog.post.application.dto.PostDto;
import ai.tech.blog.post.application.mapper.PostMapper;
import ai.tech.blog.post.domain.model.Post;
import ai.tech.blog.post.domain.repository.PostRepository;
import ai.tech.blog.user.domain.service.AuthService;
import ai.tech.blog.user.infrastructure.exception.PostNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthService authService;

    public PostServiceImpl(final PostRepository postRepository, final AuthService authService) {
        this.postRepository = postRepository;
        this.authService = authService;
    }

    @Override
    public Optional<PostDto> createPost(final PostDto postDto) {
        final Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        final User signedInUser = authService.getSignedInUser().orElseThrow(
                () -> new IllegalArgumentException("No users is signed in.")
        );
        post.setUsername(signedInUser.getUsername());
        post.setUpdatedAt(Instant.now());

        return Optional.of(PostMapper.INSTANCE.toDto(postRepository.save(post)));
    }

    @Override
    public Page<PostDto> findAll(final Pageable pageable) {
        Page<Post> postsPage = postRepository.findAll(pageable);
        Page<PostDto> postDtosPage = postsPage.map(post -> PostMapper.INSTANCE.toDto(post));
        return postDtosPage;
    }

    @Override
    public Optional<PostDto> findById(final Long id) {
        return Optional.of(PostMapper.INSTANCE.toDto(
                postRepository.findById(id).orElseThrow(
                    () -> new PostNotFoundException("Post with ID: " + id + " does not exist.")
                )
            )
        );
    }

    @Override
    public Optional<PostDto> updatePost(final Long id, final PostDto postDto) {
        final Optional<Post> foundPostOptional = Optional.of(postRepository.findById(id).get());
        if (foundPostOptional.isEmpty())
            throw new RuntimeException("Post with ID: " + id + " does not exist.");

        final Post foundPost = foundPostOptional.get();
        foundPost.setTitle(postDto.getTitle());
        foundPost.setContent(postDto.getContent());
        final User signedInUser = authService.getSignedInUser().orElseThrow(
                () -> new IllegalArgumentException("No users is signed in.")
        );
        foundPost.setUsername(signedInUser.getUsername());

        return Optional.of(PostMapper.INSTANCE.toDto(postRepository.save(foundPost)));
    }

    @Override
    public void deletePostById(final Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }
}