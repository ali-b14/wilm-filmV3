package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.VideoMetaDataTestSamples.*;
import static com.mycompany.myapp.domain.VideoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoMetaDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoMetaData.class);
        VideoMetaData videoMetaData1 = getVideoMetaDataSample1();
        VideoMetaData videoMetaData2 = new VideoMetaData();
        assertThat(videoMetaData1).isNotEqualTo(videoMetaData2);

        videoMetaData2.setId(videoMetaData1.getId());
        assertThat(videoMetaData1).isEqualTo(videoMetaData2);

        videoMetaData2 = getVideoMetaDataSample2();
        assertThat(videoMetaData1).isNotEqualTo(videoMetaData2);
    }

    @Test
    void videoTest() throws Exception {
        VideoMetaData videoMetaData = getVideoMetaDataRandomSampleGenerator();
        Video videoBack = getVideoRandomSampleGenerator();

        videoMetaData.setVideo(videoBack);
        assertThat(videoMetaData.getVideo()).isEqualTo(videoBack);
        assertThat(videoBack.getMetaData()).isEqualTo(videoMetaData);

        videoMetaData.video(null);
        assertThat(videoMetaData.getVideo()).isNull();
        assertThat(videoBack.getMetaData()).isNull();
    }
}
