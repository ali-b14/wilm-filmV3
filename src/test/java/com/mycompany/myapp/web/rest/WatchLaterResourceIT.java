package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.WatchLaterAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.WatchLater;
import com.mycompany.myapp.repository.WatchLaterRepository;
import com.mycompany.myapp.service.dto.WatchLaterDTO;
import com.mycompany.myapp.service.mapper.WatchLaterMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link WatchLaterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WatchLaterResourceIT {

    private static final String ENTITY_API_URL = "/api/watch-laters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WatchLaterRepository watchLaterRepository;

    @Autowired
    private WatchLaterMapper watchLaterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWatchLaterMockMvc;

    private WatchLater watchLater;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchLater createEntity(EntityManager em) {
        WatchLater watchLater = new WatchLater();
        return watchLater;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchLater createUpdatedEntity(EntityManager em) {
        WatchLater watchLater = new WatchLater();
        return watchLater;
    }

    @BeforeEach
    public void initTest() {
        watchLater = createEntity(em);
    }

    @Test
    @Transactional
    void createWatchLater() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);
        var returnedWatchLaterDTO = om.readValue(
            restWatchLaterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchLaterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WatchLaterDTO.class
        );

        // Validate the WatchLater in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWatchLater = watchLaterMapper.toEntity(returnedWatchLaterDTO);
        assertWatchLaterUpdatableFieldsEquals(returnedWatchLater, getPersistedWatchLater(returnedWatchLater));
    }

    @Test
    @Transactional
    void createWatchLaterWithExistingId() throws Exception {
        // Create the WatchLater with an existing ID
        watchLater.setId(1L);
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchLaterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchLaterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWatchLaters() throws Exception {
        // Initialize the database
        watchLaterRepository.saveAndFlush(watchLater);

        // Get all the watchLaterList
        restWatchLaterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchLater.getId().intValue())));
    }

    @Test
    @Transactional
    void getWatchLater() throws Exception {
        // Initialize the database
        watchLaterRepository.saveAndFlush(watchLater);

        // Get the watchLater
        restWatchLaterMockMvc
            .perform(get(ENTITY_API_URL_ID, watchLater.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchLater.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWatchLater() throws Exception {
        // Get the watchLater
        restWatchLaterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWatchLater() throws Exception {
        // Initialize the database
        watchLaterRepository.saveAndFlush(watchLater);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchLater
        WatchLater updatedWatchLater = watchLaterRepository.findById(watchLater.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWatchLater are not directly saved in db
        em.detach(updatedWatchLater);
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(updatedWatchLater);

        restWatchLaterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchLaterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchLaterDTO))
            )
            .andExpect(status().isOk());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWatchLaterToMatchAllProperties(updatedWatchLater);
    }

    @Test
    @Transactional
    void putNonExistingWatchLater() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchLater.setId(longCount.incrementAndGet());

        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchLaterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, watchLaterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchLaterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWatchLater() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchLater.setId(longCount.incrementAndGet());

        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchLaterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(watchLaterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWatchLater() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchLater.setId(longCount.incrementAndGet());

        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchLaterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(watchLaterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWatchLaterWithPatch() throws Exception {
        // Initialize the database
        watchLaterRepository.saveAndFlush(watchLater);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchLater using partial update
        WatchLater partialUpdatedWatchLater = new WatchLater();
        partialUpdatedWatchLater.setId(watchLater.getId());

        restWatchLaterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchLater.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchLater))
            )
            .andExpect(status().isOk());

        // Validate the WatchLater in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchLaterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWatchLater, watchLater),
            getPersistedWatchLater(watchLater)
        );
    }

    @Test
    @Transactional
    void fullUpdateWatchLaterWithPatch() throws Exception {
        // Initialize the database
        watchLaterRepository.saveAndFlush(watchLater);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the watchLater using partial update
        WatchLater partialUpdatedWatchLater = new WatchLater();
        partialUpdatedWatchLater.setId(watchLater.getId());

        restWatchLaterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWatchLater.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWatchLater))
            )
            .andExpect(status().isOk());

        // Validate the WatchLater in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWatchLaterUpdatableFieldsEquals(partialUpdatedWatchLater, getPersistedWatchLater(partialUpdatedWatchLater));
    }

    @Test
    @Transactional
    void patchNonExistingWatchLater() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchLater.setId(longCount.incrementAndGet());

        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchLaterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, watchLaterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchLaterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWatchLater() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchLater.setId(longCount.incrementAndGet());

        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchLaterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(watchLaterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWatchLater() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        watchLater.setId(longCount.incrementAndGet());

        // Create the WatchLater
        WatchLaterDTO watchLaterDTO = watchLaterMapper.toDto(watchLater);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWatchLaterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(watchLaterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WatchLater in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWatchLater() throws Exception {
        // Initialize the database
        watchLaterRepository.saveAndFlush(watchLater);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the watchLater
        restWatchLaterMockMvc
            .perform(delete(ENTITY_API_URL_ID, watchLater.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return watchLaterRepository.count();
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

    protected WatchLater getPersistedWatchLater(WatchLater watchLater) {
        return watchLaterRepository.findById(watchLater.getId()).orElseThrow();
    }

    protected void assertPersistedWatchLaterToMatchAllProperties(WatchLater expectedWatchLater) {
        assertWatchLaterAllPropertiesEquals(expectedWatchLater, getPersistedWatchLater(expectedWatchLater));
    }

    protected void assertPersistedWatchLaterToMatchUpdatableProperties(WatchLater expectedWatchLater) {
        assertWatchLaterAllUpdatablePropertiesEquals(expectedWatchLater, getPersistedWatchLater(expectedWatchLater));
    }
}
