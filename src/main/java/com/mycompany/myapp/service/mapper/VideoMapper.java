package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Video;
import com.mycompany.myapp.domain.VideoMetaData;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.dto.VideoDTO;
import com.mycompany.myapp.service.dto.VideoMetaDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Video} and its DTO {@link VideoDTO}.
 */
@Mapper(componentModel = "spring")
public interface VideoMapper extends EntityMapper<VideoDTO, Video> {
    @Mapping(target = "metaData", source = "metaData", qualifiedByName = "videoMetaDataId")
    @Mapping(target = "uploader", source = "uploader", qualifiedByName = "userId")
    VideoDTO toDto(Video s);

    @Named("videoMetaDataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VideoMetaDataDTO toDtoVideoMetaDataId(VideoMetaData videoMetaData);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
