package ai.tech.blog.post.application.dto;

import ai.tech.blog.base.domain.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class PostDto extends BaseEntity {
    private String title;
    private String content;
    private String username;

    @Builder
    public PostDto(Long id, Instant createdAt, Instant updatedAt, String title, String content, String username) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.content = content;
        this.username = username;
    }
}