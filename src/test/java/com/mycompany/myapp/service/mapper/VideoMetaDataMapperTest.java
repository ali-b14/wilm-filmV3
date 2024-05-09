package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.VideoMetaDataAsserts.*;
import static com.mycompany.myapp.domain.VideoMetaDataTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoMetaDataMapperTest {

    private VideoMetaDataMapper videoMetaDataMapper;

    @BeforeEach
    void setUp() {
        videoMetaDataMapper = new VideoMetaDataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVideoMetaDataSample1();
        var actual = videoMetaDataMapper.toEntity(videoMetaDataMapper.toDto(expected));
        assertVideoMetaDataAllPropertiesEquals(expected, actual);
    }
}
