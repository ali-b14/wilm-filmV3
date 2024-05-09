package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Like;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Video;
import com.mycompany.myapp.service.dto.LikeDTO;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.dto.VideoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Like} and its DTO {@link LikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LikeMapper extends EntityMapper<LikeDTO, Like> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "video", source = "video", qualifiedByName = "videoId")
    LikeDTO toDto(Like s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("videoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoDTO toDtoVideoId(Video video);
}
