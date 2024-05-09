package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Video;
import com.mycompany.myapp.domain.Watched;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.dto.VideoDTO;
import com.mycompany.myapp.service.dto.WatchedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Watched} and its DTO {@link WatchedDTO}.
 */
@Mapper(componentModel = "spring")
public interface WatchedMapper extends EntityMapper<WatchedDTO, Watched> {
    @Mapping(target = "video", source = "video", qualifiedByName = "videoId")
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userId")
    WatchedDTO toDto(Watched s);

    @Named("videoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoDTO toDtoVideoId(Video video);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
