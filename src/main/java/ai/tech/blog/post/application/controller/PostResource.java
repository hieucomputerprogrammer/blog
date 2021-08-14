package ai.tech.blog.post.application.controller;

import ai.tech.blog.post.application.dto.PostDto;
import ai.tech.blog.post.domain.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostResource {
    private final PostService postService;

    public PostResource(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Optional<PostDto>> createPost(final @RequestBody PostDto postDto) {
        return ResponseEntity.ok().body(postService.createPost(postDto));
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getPosts(final Pageable pageable) {
        return ResponseEntity.ok().body(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PostDto>> getPostById(final @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<PostDto>> updatePost(final @PathVariable("id") Long id,
                                                        final @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(final @PathVariable("id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPosts() {
        postService.deleteAll();
        return ResponseEntity.ok().build();
    }
}