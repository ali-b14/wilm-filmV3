package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.WatchedRepository;
import com.mycompany.myapp.service.WatchedService;
import com.mycompany.myapp.service.dto.WatchedDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Watched}.
 */
@RestController
@RequestMapping("/api/watcheds")
public class WatchedResource {

    private final Logger log = LoggerFactory.getLogger(WatchedResource.class);

    private static final String ENTITY_NAME = "watched";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchedService watchedService;

    private final WatchedRepository watchedRepository;

    public WatchedResource(WatchedService watchedService, WatchedRepository watchedRepository) {
        this.watchedService = watchedService;
        this.watchedRepository = watchedRepository;
    }

    /**
     * {@code POST  /watcheds} : Create a new watched.
     *
     * @param watchedDTO the watchedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchedDTO, or with status {@code 400 (Bad Request)} if the watched has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WatchedDTO> createWatched(@Valid @RequestBody WatchedDTO watchedDTO) throws URISyntaxException {
        log.debug("REST request to save Watched : {}", watchedDTO);
        if (watchedDTO.getId() != null) {
            throw new BadRequestAlertException("A new watched cannot already have an ID", ENTITY_NAME, "idexists");
        }
        watchedDTO = watchedService.save(watchedDTO);
        return ResponseEntity.created(new URI("/api/watcheds/" + watchedDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, watchedDTO.getId().toString()))
            .body(watchedDTO);
    }

    /**
     * {@code PUT  /watcheds/:id} : Updates an existing watched.
     *
     * @param id the id of the watchedDTO to save.
     * @param watchedDTO the watchedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchedDTO,
     * or with status {@code 400 (Bad Request)} if the watchedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WatchedDTO> updateWatched(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WatchedDTO watchedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Watched : {}, {}", id, watchedDTO);
        if (watchedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        watchedDTO = watchedService.update(watchedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchedDTO.getId().toString()))
            .body(watchedDTO);
    }

    /**
     * {@code PATCH  /watcheds/:id} : Partial updates given fields of an existing watched, field will ignore if it is null
     *
     * @param id the id of the watchedDTO to save.
     * @param watchedDTO the watchedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchedDTO,
     * or with status {@code 400 (Bad Request)} if the watchedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the watchedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the watchedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WatchedDTO> partialUpdateWatched(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WatchedDTO watchedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Watched partially : {}, {}", id, watchedDTO);
        if (watchedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, watchedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!watchedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WatchedDTO> result = watchedService.partialUpdate(watchedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /watcheds} : get all the watcheds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watcheds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WatchedDTO>> getAllWatcheds(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Watcheds");
        Page<WatchedDTO> page = watchedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watcheds/:id} : get the "id" watched.
     *
     * @param id the id of the watchedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WatchedDTO> getWatched(@PathVariable("id") Long id) {
        log.debug("REST request to get Watched : {}", id);
        Optional<WatchedDTO> watchedDTO = watchedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchedDTO);
    }

    /**
     * {@code DELETE  /watcheds/:id} : delete the "id" watched.
     *
     * @param id the id of the watchedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatched(@PathVariable("id") Long id) {
        log.debug("REST request to delete Watched : {}", id);
        watchedService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
