package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.VideoMetaDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.VideoMetaData}.
 */
public interface VideoMetaDataService {
    /**
     * Save a videoMetaData.
     *
     * @param videoMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    VideoMetaDataDTO save(VideoMetaDataDTO videoMetaDataDTO);

    /**
     * Updates a videoMetaData.
     *
     * @param videoMetaDataDTO the entity to update.
     * @return the persisted entity.
     */
    VideoMetaDataDTO update(VideoMetaDataDTO videoMetaDataDTO);

    /**
     * Partially updates a videoMetaData.
     *
     * @param videoMetaDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VideoMetaDataDTO> partialUpdate(VideoMetaDataDTO videoMetaDataDTO);

    /**
     * Get all the videoMetaData.
     *
     * @return the list of entities.
     */
    List<VideoMetaDataDTO> findAll();

    /**
     * Get all the VideoMetaDataDTO where Video is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<VideoMetaDataDTO> findAllWhereVideoIsNull();

    /**
     * Get the "id" videoMetaData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VideoMetaDataDTO> findOne(Long id);

    /**
     * Delete the "id" videoMetaData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
