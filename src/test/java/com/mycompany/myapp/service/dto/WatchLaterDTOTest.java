package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WatchLaterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchLaterDTO.class);
        WatchLaterDTO watchLaterDTO1 = new WatchLaterDTO();
        watchLaterDTO1.setId(1L);
        WatchLaterDTO watchLaterDTO2 = new WatchLaterDTO();
        assertThat(watchLaterDTO1).isNotEqualTo(watchLaterDTO2);
        watchLaterDTO2.setId(watchLaterDTO1.getId());
        assertThat(watchLaterDTO1).isEqualTo(watchLaterDTO2);
        watchLaterDTO2.setId(2L);
        assertThat(watchLaterDTO1).isNotEqualTo(watchLaterDTO2);
        watchLaterDTO1.setId(null);
        assertThat(watchLaterDTO1).isNotEqualTo(watchLaterDTO2);
    }
}
