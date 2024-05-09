package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.WatchLater;
import com.mycompany.myapp.repository.WatchLaterRepository;
import com.mycompany.myapp.service.WatchLaterService;
import com.mycompany.myapp.service.dto.WatchLaterDTO;
import com.mycompany.myapp.service.mapper.WatchLaterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.WatchLater}.
 */
@Service
@Transactional
public class WatchLaterServiceImpl implements WatchLaterService {

    private final Logger log = LoggerFactory.getLogger(WatchLaterServiceImpl.class);

    private final WatchLaterRepository watchLaterRepository;

    private final WatchLaterMapper watchLaterMapper;

    public WatchLaterServiceImpl(WatchLaterRepository watchLaterRepository, WatchLaterMapper watchLaterMapper) {
        this.watchLaterRepository = watchLaterRepository;
        this.watchLaterMapper = watchLaterMapper;
    }

    @Override
    public WatchLaterDTO save(WatchLaterDTO watchLaterDTO) {
        log.debug("Request to save WatchLater : {}", watchLaterDTO);
        WatchLater watchLater = watchLaterMapper.toEntity(watchLaterDTO);
        watchLater = watchLaterRepository.save(watchLater);
        return watchLaterMapper.toDto(watchLater);
    }

    @Override
    public WatchLaterDTO update(WatchLaterDTO watchLaterDTO) {
        log.debug("Request to update WatchLater : {}", watchLaterDTO);
        WatchLater watchLater = watchLaterMapper.toEntity(watchLaterDTO);
        watchLater = watchLaterRepository.save(watchLater);
        return watchLaterMapper.toDto(watchLater);
    }

    @Override
    public Optional<WatchLaterDTO> partialUpdate(WatchLaterDTO watchLaterDTO) {
        log.debug("Request to partially update WatchLater : {}", watchLaterDTO);

        return watchLaterRepository
            .findById(watchLaterDTO.getId())
            .map(existingWatchLater -> {
                watchLaterMapper.partialUpdate(existingWatchLater, watchLaterDTO);

                return existingWatchLater;
            })
            .map(watchLaterRepository::save)
            .map(watchLaterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WatchLaterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WatchLaters");
        return watchLaterRepository.findAll(pageable).map(watchLaterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WatchLaterDTO> findOne(Long id) {
        log.debug("Request to get WatchLater : {}", id);
        return watchLaterRepository.findById(id).map(watchLaterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WatchLater : {}", id);
        watchLaterRepository.deleteById(id);
    }
}
