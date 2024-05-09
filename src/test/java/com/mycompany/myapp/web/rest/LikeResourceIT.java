package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.LikeAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Like;
import com.mycompany.myapp.repository.LikeRepository;
import com.mycompany.myapp.service.dto.LikeDTO;
import com.mycompany.myapp.service.mapper.LikeMapper;
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
 * Integration tests for the {@link LikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LikeResourceIT {

    private static final Instant DEFAULT_LIKED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LIKED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeMockMvc;

    private Like like;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createEntity(EntityManager em) {
        Like like = new Like().likedAt(DEFAULT_LIKED_AT);
        return like;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Like createUpdatedEntity(EntityManager em) {
        Like like = new Like().likedAt(UPDATED_LIKED_AT);
        return like;
    }

    @BeforeEach
    public void initTest() {
        like = createEntity(em);
    }

    @Test
    @Transactional
    void createLike() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);
        var returnedLikeDTO = om.readValue(
            restLikeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(likeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LikeDTO.class
        );

        // Validate the Like in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLike = likeMapper.toEntity(returnedLikeDTO);
        assertLikeUpdatableFieldsEquals(returnedLike, getPersistedLike(returnedLike));
    }

    @Test
    @Transactional
    void createLikeWithExistingId() throws Exception {
        // Create the Like with an existing ID
        like.setId(1L);
        LikeDTO likeDTO = likeMapper.toDto(like);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(likeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLikedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        like.setLikedAt(null);

        // Create the Like, which fails.
        LikeDTO likeDTO = likeMapper.toDto(like);

        restLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(likeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLikes() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList
        restLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].likedAt").value(hasItem(DEFAULT_LIKED_AT.toString())));
    }

    @Test
    @Transactional
    void getLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get the like
        restLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(like.getId().intValue()))
            .andExpect(jsonPath("$.likedAt").value(DEFAULT_LIKED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLike() throws Exception {
        // Get the like
        restLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the like
        Like updatedLike = likeRepository.findById(like.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLike are not directly saved in db
        em.detach(updatedLike);
        updatedLike.likedAt(UPDATED_LIKED_AT);
        LikeDTO likeDTO = likeMapper.toDto(updatedLike);

        restLikeMockMvc
            .perform(put(ENTITY_API_URL_ID, likeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(likeDTO)))
            .andExpect(status().isOk());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLikeToMatchAllProperties(updatedLike);
    }

    @Test
    @Transactional
    void putNonExistingLike() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        like.setId(longCount.incrementAndGet());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(put(ENTITY_API_URL_ID, likeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(likeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLike() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        like.setId(longCount.incrementAndGet());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLike() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        like.setId(longCount.incrementAndGet());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(likeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLikeWithPatch() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the like using partial update
        Like partialUpdatedLike = new Like();
        partialUpdatedLike.setId(like.getId());

        partialUpdatedLike.likedAt(UPDATED_LIKED_AT);

        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLikeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLike, like), getPersistedLike(like));
    }

    @Test
    @Transactional
    void fullUpdateLikeWithPatch() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the like using partial update
        Like partialUpdatedLike = new Like();
        partialUpdatedLike.setId(like.getId());

        partialUpdatedLike.likedAt(UPDATED_LIKED_AT);

        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLike))
            )
            .andExpect(status().isOk());

        // Validate the Like in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLikeUpdatableFieldsEquals(partialUpdatedLike, getPersistedLike(partialUpdatedLike));
    }

    @Test
    @Transactional
    void patchNonExistingLike() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        like.setId(longCount.incrementAndGet());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, likeDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLike() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        like.setId(longCount.incrementAndGet());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(likeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLike() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        like.setId(longCount.incrementAndGet());

        // Create the Like
        LikeDTO likeDTO = likeMapper.toDto(like);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(likeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Like in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the like
        restLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, like.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return likeRepository.count();
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

    protected Like getPersistedLike(Like like) {
        return likeRepository.findById(like.getId()).orElseThrow();
    }

    protected void assertPersistedLikeToMatchAllProperties(Like expectedLike) {
        assertLikeAllPropertiesEquals(expectedLike, getPersistedLike(expectedLike));
    }

    protected void assertPersistedLikeToMatchUpdatableProperties(Like expectedLike) {
        assertLikeAllUpdatablePropertiesEquals(expectedLike, getPersistedLike(expectedLike));
    }
}
