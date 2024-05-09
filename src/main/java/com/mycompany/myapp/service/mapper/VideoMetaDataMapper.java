package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.VideoMetaData;
import com.mycompany.myapp.service.dto.VideoMetaDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VideoMetaData} and its DTO {@link VideoMetaDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface VideoMetaDataMapper extends EntityMapper<VideoMetaDataDTO, VideoMetaData> {}
