package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class WatchLaterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WatchLater getWatchLaterSample1() {
        return new WatchLater().id(1L);
    }

    public static WatchLater getWatchLaterSample2() {
        return new WatchLater().id(2L);
    }

    public static WatchLater getWatchLaterRandomSampleGenerator() {
        return new WatchLater().id(longCount.incrementAndGet());
    }
}
