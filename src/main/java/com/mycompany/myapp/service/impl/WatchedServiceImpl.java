package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Watched;
import com.mycompany.myapp.repository.WatchedRepository;
import com.mycompany.myapp.service.WatchedService;
import com.mycompany.myapp.service.dto.WatchedDTO;
import com.mycompany.myapp.service.mapper.WatchedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Watched}.
 */
@Service
@Transactional
public class WatchedServiceImpl implements WatchedService {

    private final Logger log = LoggerFactory.getLogger(WatchedServiceImpl.class);

    private final WatchedRepository watchedRepository;

    private final WatchedMapper watchedMapper;

    public WatchedServiceImpl(WatchedRepository watchedRepository, WatchedMapper watchedMapper) {
        this.watchedRepository = watchedRepository;
        this.watchedMapper = watchedMapper;
    }

    @Override
    public WatchedDTO save(WatchedDTO watchedDTO) {
        log.debug("Request to save Watched : {}", watchedDTO);
        Watched watched = watchedMapper.toEntity(watchedDTO);
        watched = watchedRepository.save(watched);
        return watchedMapper.toDto(watched);
    }

    @Override
    public WatchedDTO update(WatchedDTO watchedDTO) {
        log.debug("Request to update Watched : {}", watchedDTO);
        Watched watched = watchedMapper.toEntity(watchedDTO);
        watched = watchedRepository.save(watched);
        return watchedMapper.toDto(watched);
    }

    @Override
    public Optional<WatchedDTO> partialUpdate(WatchedDTO watchedDTO) {
        log.debug("Request to partially update Watched : {}", watchedDTO);

        return watchedRepository
            .findById(watchedDTO.getId())
            .map(existingWatched -> {
                watchedMapper.partialUpdate(existingWatched, watchedDTO);

                return existingWatched;
            })
            .map(watchedRepository::save)
            .map(watchedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WatchedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Watcheds");
        return watchedRepository.findAll(pageable).map(watchedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WatchedDTO> findOne(Long id) {
        log.debug("Request to get Watched : {}", id);
        return watchedRepository.findById(id).map(watchedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Watched : {}", id);
        watchedRepository.deleteById(id);
    }
}
