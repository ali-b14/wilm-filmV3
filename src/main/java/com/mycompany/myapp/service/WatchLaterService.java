package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.WatchLaterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.WatchLater}.
 */
public interface WatchLaterService {
    /**
     * Save a watchLater.
     *
     * @param watchLaterDTO the entity to save.
     * @return the persisted entity.
     */
    WatchLaterDTO save(WatchLaterDTO watchLaterDTO);

    /**
     * Updates a watchLater.
     *
     * @param watchLaterDTO the entity to update.
     * @return the persisted entity.
     */
    WatchLaterDTO update(WatchLaterDTO watchLaterDTO);

    /**
     * Partially updates a watchLater.
     *
     * @param watchLaterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WatchLaterDTO> partialUpdate(WatchLaterDTO watchLaterDTO);

    /**
     * Get all the watchLaters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WatchLaterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" watchLater.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WatchLaterDTO> findOne(Long id);

    /**
     * Delete the "id" watchLater.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
