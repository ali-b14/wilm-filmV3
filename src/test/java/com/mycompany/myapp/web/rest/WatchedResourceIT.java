package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.WatchedAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Watched;
import com.mycompany.myapp.repository.WatchedRepository;
import com.mycompany.myapp.service.dto.WatchedDTO;
import com.mycompany.myapp.service.mapper.WatchedMapper;
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
 * Integration tests for the {@link WatchedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchedResourceIT {

    private static final Instant DEFAULT_WATCHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WATCHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/watcheds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WatchedRepository watchedRepository;

    @Autowired
    private WatchedMapper watchedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchedMockMvc;

    private Watched watched;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Watched createEntity(EntityManager em) {
        Watched watched = new Watched().watchedAt(DEFAULT_WATCHED_AT);
        return watched;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Watched createUpdatedEntity(EntityManager em) {
        Watched watched = new Watched().watchedAt(UPDATED_WATCHED_AT);
        return watched;
    }

    @BeforeEach
    public void initTest() {
        watched = createEntity(em);
    }

    @Test
    @Transactional
    void createWatched() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);
        var returnedWatchedDTO = om.readValue(
            restWatchedMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchedDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WatchedDTO.class
        );

        // Validate the Watched in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWatched = watchedMapper.toEntity(returnedWatchedDTO);
        assertWatchedUpdatableFieldsEquals(returnedWatched, getPersistedWatched(returnedWatched));
    }

    @Test
    @Transactional
    void createWatchedWithExistingId() throws Exception {
        // Create the Watched with an existing ID
        watched.setId(1L);
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWatchedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        watched.setWatchedAt(null);

        // Create the Watched, which fails.
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        restWatchedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchedDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWatcheds() throws Exception {
        // Initialize the database
        watchedRepository.saveAndFlush(watched);

        // Get all the watchedList
        restWatchedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watched.getId().intValue())))
            .andExpect(jsonPath("$.[*].watchedAt").value(hasItem(DEFAULT_WATCHED_AT.toString())));
    }

    @Test
    @Transactional
    void getWatched() throws Exception {
        // Initialize the database
        watchedRepository.saveAndFlush(watched);

        // Get the watched
        restWatchedMockMvc
            .perform(get(ENTITY_API_URL_ID, watched.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watched.getId().intValue()))
            .andExpect(jsonPath("$.watchedAt").value(DEFAULT_WATCHED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWatched() throws Exception {
        // Get the watched
        restWatchedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWatched() throws Exception {
        // Initialize the database
        watchedRepository.saveAndFlush(watched);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watched
        Watched updatedWatched = watchedRepository.findById(watched.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWatched are not directly saved in db
        em.detach(updatedWatched);
        updatedWatched.watchedAt(UPDATED_WATCHED_AT);
        WatchedDTO watchedDTO = watchedMapper.toDto(updatedWatched);

        restWatchedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchedDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchedDTO))
            )
            .andExpect(status().isOk());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWatchedToMatchAllProperties(updatedWatched);
    }

    @Test
    @Transactional
    void putNonExistingWatched() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watched.setId(longCount.incrementAndGet());

        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchedDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatched() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watched.setId(longCount.incrementAndGet());

        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatched() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watched.setId(longCount.incrementAndGet());

        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchedDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchedWithPatch() throws Exception {
        // Initialize the database
        watchedRepository.saveAndFlush(watched);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watched using partial update
        Watched partialUpdatedWatched = new Watched();
        partialUpdatedWatched.setId(watched.getId());

        partialUpdatedWatched.watchedAt(UPDATED_WATCHED_AT);

        restWatchedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatched.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatched))
            )
            .andExpect(status().isOk());

        // Validate the Watched in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchedUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWatched, watched), getPersistedWatched(watched));
    }

    @Test
    @Transactional
    void fullUpdateWatchedWithPatch() throws Exception {
        // Initialize the database
        watchedRepository.saveAndFlush(watched);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watched using partial update
        Watched partialUpdatedWatched = new Watched();
        partialUpdatedWatched.setId(watched.getId());

        partialUpdatedWatched.watchedAt(UPDATED_WATCHED_AT);

        restWatchedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatched.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatched))
            )
            .andExpect(status().isOk());

        // Validate the Watched in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchedUpdatableFieldsEquals(partialUpdatedWatched, getPersistedWatched(partialUpdatedWatched));
    }

    @Test
    @Transactional
    void patchNonExistingWatched() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watched.setId(longCount.incrementAndGet());

        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatched() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watched.setId(longCount.incrementAndGet());

        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatched() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watched.setId(longCount.incrementAndGet());

        // Create the Watched
        WatchedDTO watchedDTO = watchedMapper.toDto(watched);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(watchedDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Watched in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatched() throws Exception {
        // Initialize the database
        watchedRepository.saveAndFlush(watched);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the watched
        restWatchedMockMvc
            .perform(delete(ENTITY_API_URL_ID, watched.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return watchedRepository.count();
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

    protected Watched getPersistedWatched(Watched watched) {
        return watchedRepository.findById(watched.getId()).orElseThrow();
    }

    protected void assertPersistedWatchedToMatchAllProperties(Watched expectedWatched) {
        assertWatchedAllPropertiesEquals(expectedWatched, getPersistedWatched(expectedWatched));
    }

    protected void assertPersistedWatchedToMatchUpdatableProperties(Watched expectedWatched) {
        assertWatchedAllUpdatablePropertiesEquals(expectedWatched, getPersistedWatched(expectedWatched));
    }
}
