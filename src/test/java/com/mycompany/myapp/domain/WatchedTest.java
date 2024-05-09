package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.VideoTestSamples.*;
import static com.mycompany.myapp.domain.WatchedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Watched.class);
        Watched watched1 = getWatchedSample1();
        Watched watched2 = new Watched();
        assertThat(watched1).isNotEqualTo(watched2);

        watched2.setId(watched1.getId());
        assertThat(watched1).isEqualTo(watched2);

        watched2 = getWatchedSample2();
        assertThat(watched1).isNotEqualTo(watched2);
    }

    @Test
    void videoTest() throws Exception {
        Watched watched = getWatchedRandomSampleGenerator();
        Video videoBack = getVideoRandomSampleGenerator();

        watched.setVideo(videoBack);
        assertThat(watched.getVideo()).isEqualTo(videoBack);

        watched.video(null);
        assertThat(watched.getVideo()).isNull();
    }
}
