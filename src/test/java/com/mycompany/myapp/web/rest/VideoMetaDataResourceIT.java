package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.VideoMetaDataAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.VideoMetaData;
import com.mycompany.myapp.repository.VideoMetaDataRepository;
import com.mycompany.myapp.service.dto.VideoMetaDataDTO;
import com.mycompany.myapp.service.mapper.VideoMetaDataMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VideoMetaDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideoMetaDataResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/video-meta-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VideoMetaDataRepository videoMetaDataRepository;

    @Autowired
    private VideoMetaDataMapper videoMetaDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVideoMetaDataMockMvc;

    private VideoMetaData videoMetaData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoMetaData createEntity(EntityManager em) {
        VideoMetaData videoMetaData = new VideoMetaData()
            .title(DEFAULT_TITLE)
            .genre(DEFAULT_GENRE)
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .description(DEFAULT_DESCRIPTION);
        return videoMetaData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VideoMetaData createUpdatedEntity(EntityManager em) {
        VideoMetaData videoMetaData = new VideoMetaData()
            .title(UPDATED_TITLE)
            .genre(UPDATED_GENRE)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .description(UPDATED_DESCRIPTION);
        return videoMetaData;
    }

    @BeforeEach
    public void initTest() {
        videoMetaData = createEntity(em);
    }

    @Test
    @Transactional
    void createVideoMetaData() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);
        var returnedVideoMetaDataDTO = om.readValue(
            restVideoMetaDataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoMetaDataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VideoMetaDataDTO.class
        );

        // Validate the VideoMetaData in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVideoMetaData = videoMetaDataMapper.toEntity(returnedVideoMetaDataDTO);
        assertVideoMetaDataUpdatableFieldsEquals(returnedVideoMetaData, getPersistedVideoMetaData(returnedVideoMetaData));
    }

    @Test
    @Transactional
    void createVideoMetaDataWithExistingId() throws Exception {
        // Create the VideoMetaData with an existing ID
        videoMetaData.setId(1L);
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideoMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        videoMetaData.setTitle(null);

        // Create the VideoMetaData, which fails.
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        restVideoMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoMetaDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        videoMetaData.setGenre(null);

        // Create the VideoMetaData, which fails.
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        restVideoMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoMetaDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUploadDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        videoMetaData.setUploadDate(null);

        // Create the VideoMetaData, which fails.
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        restVideoMetaDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoMetaDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVideoMetaData() throws Exception {
        // Initialize the database
        videoMetaDataRepository.saveAndFlush(videoMetaData);

        // Get all the videoMetaDataList
        restVideoMetaDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videoMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getVideoMetaData() throws Exception {
        // Initialize the database
        videoMetaDataRepository.saveAndFlush(videoMetaData);

        // Get the videoMetaData
        restVideoMetaDataMockMvc
            .perform(get(ENTITY_API_URL_ID, videoMetaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(videoMetaData.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingVideoMetaData() throws Exception {
        // Get the videoMetaData
        restVideoMetaDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVideoMetaData() throws Exception {
        // Initialize the database
        videoMetaDataRepository.saveAndFlush(videoMetaData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the videoMetaData
        VideoMetaData updatedVideoMetaData = videoMetaDataRepository.findById(videoMetaData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVideoMetaData are not directly saved in db
        em.detach(updatedVideoMetaData);
        updatedVideoMetaData.title(UPDATED_TITLE).genre(UPDATED_GENRE).uploadDate(UPDATED_UPLOAD_DATE).description(UPDATED_DESCRIPTION);
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(updatedVideoMetaData);

        restVideoMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videoMetaDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(videoMetaDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVideoMetaDataToMatchAllProperties(updatedVideoMetaData);
    }

    @Test
    @Transactional
    void putNonExistingVideoMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoMetaData.setId(longCount.incrementAndGet());

        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, videoMetaDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(videoMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVideoMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoMetaData.setId(longCount.incrementAndGet());

        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMetaDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(videoMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVideoMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoMetaData.setId(longCount.incrementAndGet());

        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMetaDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(videoMetaDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVideoMetaDataWithPatch() throws Exception {
        // Initialize the database
        videoMetaDataRepository.saveAndFlush(videoMetaData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the videoMetaData using partial update
        VideoMetaData partialUpdatedVideoMetaData = new VideoMetaData();
        partialUpdatedVideoMetaData.setId(videoMetaData.getId());

        partialUpdatedVideoMetaData.title(UPDATED_TITLE).genre(UPDATED_GENRE);

        restVideoMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoMetaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVideoMetaData))
            )
            .andExpect(status().isOk());

        // Validate the VideoMetaData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVideoMetaDataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVideoMetaData, videoMetaData),
            getPersistedVideoMetaData(videoMetaData)
        );
    }

    @Test
    @Transactional
    void fullUpdateVideoMetaDataWithPatch() throws Exception {
        // Initialize the database
        videoMetaDataRepository.saveAndFlush(videoMetaData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the videoMetaData using partial update
        VideoMetaData partialUpdatedVideoMetaData = new VideoMetaData();
        partialUpdatedVideoMetaData.setId(videoMetaData.getId());

        partialUpdatedVideoMetaData
            .title(UPDATED_TITLE)
            .genre(UPDATED_GENRE)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .description(UPDATED_DESCRIPTION);

        restVideoMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVideoMetaData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVideoMetaData))
            )
            .andExpect(status().isOk());

        // Validate the VideoMetaData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVideoMetaDataUpdatableFieldsEquals(partialUpdatedVideoMetaData, getPersistedVideoMetaData(partialUpdatedVideoMetaData));
    }

    @Test
    @Transactional
    void patchNonExistingVideoMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoMetaData.setId(longCount.incrementAndGet());

        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVideoMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, videoMetaDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(videoMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVideoMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoMetaData.setId(longCount.incrementAndGet());

        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMetaDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(videoMetaDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVideoMetaData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        videoMetaData.setId(longCount.incrementAndGet());

        // Create the VideoMetaData
        VideoMetaDataDTO videoMetaDataDTO = videoMetaDataMapper.toDto(videoMetaData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVideoMetaDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(videoMetaDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VideoMetaData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVideoMetaData() throws Exception {
        // Initialize the database
        videoMetaDataRepository.saveAndFlush(videoMetaData);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the videoMetaData
        restVideoMetaDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, videoMetaData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return videoMetaDataRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected VideoMetaData getPersistedVideoMetaData(VideoMetaData videoMetaData) {
        return videoMetaDataRepository.findById(videoMetaData.getId()).orElseThrow();
    }

    protected void assertPersistedVideoMetaDataToMatchAllProperties(VideoMetaData expectedVideoMetaData) {
        assertVideoMetaDataAllPropertiesEquals(expectedVideoMetaData, getPersistedVideoMetaData(expectedVideoMetaData));
    }

    protected void assertPersistedVideoMetaDataToMatchUpdatableProperties(VideoMetaData expectedVideoMetaData) {
        assertVideoMetaDataAllUpdatablePropertiesEquals(expectedVideoMetaData, getPersistedVideoMetaData(expectedVideoMetaData));
    }
}
