package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.WatchLaterRepository;
import com.mycompany.myapp.service.WatchLaterService;
import com.mycompany.myapp.service.dto.WatchLaterDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.WatchLater}.
 */
@RestController
@RequestMapping("/api/watch-laters")
public class WatchLaterResource {

    private final Logger log = LoggerFactory.getLogger(WatchLaterResource.class);

    private static final String ENTITY_NAME = "watchLater";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchLaterService watchLaterService;

    private final WatchLaterRepository watchLaterRepository;

    public WatchLaterResource(WatchLaterService watchLaterService, WatchLaterRepository watchLaterRepository) {
        this.watchLaterService = watchLaterService;
        this.watchLaterRepository = watchLaterRepository;
    }

    /**
     * {@code POST  /watch-laters} : Create a new watchLater.
     *
     * @param watchLaterDTO the watchLaterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchLaterDTO, or with status {@code 400 (Bad Request)} if the watchLater has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WatchLaterDTO> createWatchLater(@RequestBody WatchLaterDTO watchLaterDTO) throws URISyntaxException {
        log.debug("REST request to save WatchLater : {}", watchLaterDTO);
        if (watchLaterDTO.getId() != null) {
            throw new BadRequestAlertException("A new watchLater cannot already have an ID", ENTITY_NAME, "idexists");
        }
        watchLaterDTO = watchLaterService.save(watchLaterDTO);
        return ResponseEntity.created(new URI("/api/watch-laters/" + watchLaterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, watchLaterDTO.getId().toString()))
            .body(watchLaterDTO);
    }

    /**
     * {@code PUT  /watch-laters/:id} : Updates an existing watchLater.
     *
     * @param id the id of the watchLaterDTO to save.
     * @param watchLaterDTO the watchLaterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchLaterDTO,
     * or with status {@code 400 (Bad Request)} if the watchLaterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchLaterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WatchLaterDTO> updateWatchLater(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WatchLaterDTO watchLaterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WatchLater : {}, {}", id, watchLaterDTO);
        if (watchLaterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchLaterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchLaterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        watchLaterDTO = watchLaterService.update(watchLaterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchLaterDTO.getId().toString()))
            .body(watchLaterDTO);
    }

    /**
     * {@code PATCH  /watch-laters/:id} : Partial updates given fields of an existing watchLater, field will ignore if it is null
     *
     * @param id the id of the watchLaterDTO to save.
     * @param watchLaterDTO the watchLaterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchLaterDTO,
     * or with status {@code 400 (Bad Request)} if the watchLaterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the watchLaterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the watchLaterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WatchLaterDTO> partialUpdateWatchLater(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WatchLaterDTO watchLaterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WatchLater partially : {}, {}", id, watchLaterDTO);
        if (watchLaterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchLaterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchLaterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WatchLaterDTO> result = watchLaterService.partialUpdate(watchLaterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchLaterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /watch-laters} : get all the watchLaters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchLaters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WatchLaterDTO>> getAllWatchLaters(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of WatchLaters");
        Page<WatchLaterDTO> page = watchLaterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watch-laters/:id} : get the "id" watchLater.
     *
     * @param id the id of the watchLaterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchLaterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WatchLaterDTO> getWatchLater(@PathVariable("id") Long id) {
        log.debug("REST request to get WatchLater : {}", id);
        Optional<WatchLaterDTO> watchLaterDTO = watchLaterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchLaterDTO);
    }

    /**
     * {@code DELETE  /watch-laters/:id} : delete the "id" watchLater.
     *
     * @param id the id of the watchLaterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchLater(@PathVariable("id") Long id) {
        log.debug("REST request to delete WatchLater : {}", id);
        watchLaterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
