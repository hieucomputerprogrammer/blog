package ai.tech.blog.post.application.mapper;

import ai.tech.blog.post.application.dto.PostDto;
import ai.tech.blog.post.domain.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    public static final PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto toDto(final Post post);

    Post toEntity(final PostDto postDto);
}