package ai.tech.blog.post.domain.model;

import ai.tech.blog.base.domain.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Post extends BaseEntity {
    @NotBlank
    @Column(name = "title")
    private String title;

    @Lob
    @NotEmpty
    @Column(name = "content")
    private String content;

    @NotBlank
    @Column(name = "username")
    private String username;

    @Builder
    public Post(Long id, Instant createdAt, Instant updatedAt, String title, String content, String username) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.content = content;
        this.username = username;
    }
}