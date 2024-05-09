package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.VideoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Video}.
 */
public interface VideoService {
    /**
     * Save a video.
     *
     * @param videoDTO the entity to save.
     * @return the persisted entity.
     */
    VideoDTO save(VideoDTO videoDTO);

    /**
     * Updates a video.
     *
     * @param videoDTO the entity to update.
     * @return the persisted entity.
     */
    VideoDTO update(VideoDTO videoDTO);

    /**
     * Partially updates a video.
     *
     * @param videoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VideoDTO> partialUpdate(VideoDTO videoDTO);

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VideoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" video.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VideoDTO> findOne(Long id);

    /**
     * Delete the "id" video.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
