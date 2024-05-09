package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.WatchedAsserts.*;
import static com.mycompany.myapp.domain.WatchedTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WatchedMapperTest {

    private WatchedMapper watchedMapper;

    @BeforeEach
    void setUp() {
        watchedMapper = new WatchedMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWatchedSample1();
        var actual = watchedMapper.toEntity(watchedMapper.toDto(expected));
        assertWatchedAllPropertiesEquals(expected, actual);
    }
}
