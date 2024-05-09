package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchedDTO.class);
        WatchedDTO watchedDTO1 = new WatchedDTO();
        watchedDTO1.setId(1L);
        WatchedDTO watchedDTO2 = new WatchedDTO();
        assertThat(watchedDTO1).isNotEqualTo(watchedDTO2);
        watchedDTO2.setId(watchedDTO1.getId());
        assertThat(watchedDTO1).isEqualTo(watchedDTO2);
        watchedDTO2.setId(2L);
        assertThat(watchedDTO1).isNotEqualTo(watchedDTO2);
        watchedDTO1.setId(null);
        assertThat(watchedDTO1).isNotEqualTo(watchedDTO2);
    }
}
