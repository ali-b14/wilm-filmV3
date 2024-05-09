package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Video;
import com.mycompany.myapp.service.dto.CommentDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.dto.VideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "author", source = "author", qualifiedByName = "userId")
    @Mapping(target = "video", source = "video", qualifiedByName = "videoId")
    CommentDTO toDto(Comment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("videoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoDTO toDtoVideoId(Video video);
}
