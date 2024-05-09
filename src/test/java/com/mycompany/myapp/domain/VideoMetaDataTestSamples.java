package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VideoMetaDataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static VideoMetaData getVideoMetaDataSample1() {
        return new VideoMetaData().id(1L).title("title1").genre("genre1").description("description1");
    }

    public static VideoMetaData getVideoMetaDataSample2() {
        return new VideoMetaData().id(2L).title("title2").genre("genre2").description("description2");
    }

    public static VideoMetaData getVideoMetaDataRandomSampleGenerator() {
        return new VideoMetaData()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .genre(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
