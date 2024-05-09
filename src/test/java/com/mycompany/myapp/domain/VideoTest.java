package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.VideoMetaDataTestSamples.*;
import static com.mycompany.myapp.domain.VideoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Video.class);
        Video video1 = getVideoSample1();
        Video video2 = new Video();
        assertThat(video1).isNotEqualTo(video2);

        video2.setId(video1.getId());
        assertThat(video1).isEqualTo(video2);

        video2 = getVideoSample2();
        assertThat(video1).isNotEqualTo(video2);
    }

    @Test
    void metaDataTest() throws Exception {
        Video video = getVideoRandomSampleGenerator();
        VideoMetaData videoMetaDataBack = getVideoMetaDataRandomSampleGenerator();

        video.setMetaData(videoMetaDataBack);
        assertThat(video.getMetaData()).isEqualTo(videoMetaDataBack);

        video.metaData(null);
        assertThat(video.getMetaData()).isNull();
    }
}
