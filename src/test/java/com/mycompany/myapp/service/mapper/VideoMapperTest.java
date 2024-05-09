package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.VideoAsserts.*;
import static com.mycompany.myapp.domain.VideoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoMapperTest {

    private VideoMapper videoMapper;

    @BeforeEach
    void setUp() {
        videoMapper = new VideoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVideoSample1();
        var actual = videoMapper.toEntity(videoMapper.toDto(expected));
        assertVideoAllPropertiesEquals(expected, actual);
    }
}
