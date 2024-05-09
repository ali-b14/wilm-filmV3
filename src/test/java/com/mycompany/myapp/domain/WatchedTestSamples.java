package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class WatchedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Watched getWatchedSample1() {
        return new Watched().id(1L);
    }

    public static Watched getWatchedSample2() {
        return new Watched().id(2L);
    }

    public static Watched getWatchedRandomSampleGenerator() {
        return new Watched().id(longCount.incrementAndGet());
    }
}
