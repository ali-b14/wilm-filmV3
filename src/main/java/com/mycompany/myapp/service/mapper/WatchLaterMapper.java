package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Video;
import com.mycompany.myapp.domain.WatchLater;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.dto.VideoDTO;
import com.mycompany.myapp.service.dto.WatchLaterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WatchLater} and its DTO {@link WatchLaterDTO}.
 */
@Mapper(componentModel = "spring")
public interface WatchLaterMapper extends EntityMapper<WatchLaterDTO, WatchLater> {
    @Mapping(target = "video", source = "video", qualifiedByName = "videoId")
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userId")
    WatchLaterDTO toDto(WatchLater s);

    @Named("videoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoDTO toDtoVideoId(Video video);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
