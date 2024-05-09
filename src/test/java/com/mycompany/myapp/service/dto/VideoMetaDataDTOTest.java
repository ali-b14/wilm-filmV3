package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideoMetaDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoMetaDataDTO.class);
        VideoMetaDataDTO videoMetaDataDTO1 = new VideoMetaDataDTO();
        videoMetaDataDTO1.setId(1L);
        VideoMetaDataDTO videoMetaDataDTO2 = new VideoMetaDataDTO();
        assertThat(videoMetaDataDTO1).isNotEqualTo(videoMetaDataDTO2);
        videoMetaDataDTO2.setId(videoMetaDataDTO1.getId());
        assertThat(videoMetaDataDTO1).isEqualTo(videoMetaDataDTO2);
        videoMetaDataDTO2.setId(2L);
        assertThat(videoMetaDataDTO1).isNotEqualTo(videoMetaDataDTO2);
        videoMetaDataDTO1.setId(null);
        assertThat(videoMetaDataDTO1).isNotEqualTo(videoMetaDataDTO2);
    }
}
