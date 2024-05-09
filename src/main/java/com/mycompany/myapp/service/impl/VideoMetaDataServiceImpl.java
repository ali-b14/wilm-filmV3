package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.VideoMetaData;
import com.mycompany.myapp.repository.VideoMetaDataRepository;
import com.mycompany.myapp.service.VideoMetaDataService;
import com.mycompany.myapp.service.dto.VideoMetaDataDTO;
import com.mycompany.myapp.service.mapper.VideoMetaDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.VideoMetaData}.
 */
@Service
@Transactional
public class VideoMetaDataServiceImpl implements VideoMetaDataService {

    private final Logger log = LoggerFactory.getLogger(VideoMetaDataServiceImpl.class);

    private final VideoMetaDataRepository videoMetaDataRepository;

    private final VideoMetaDataMapper videoMetaDataMapper;

    public VideoMetaDataServiceImpl(VideoMetaDataRepository videoMetaDataRepository, VideoMetaDataMapper videoMetaDataMapper) {
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.videoMetaDataMapper = videoMetaDataMapper;
    }

    @Override
    public VideoMetaDataDTO save(VideoMetaDataDTO videoMetaDataDTO) {
        log.debug("Request to save VideoMetaData : {}", videoMetaDataDTO);
        VideoMetaData videoMetaData = videoMetaDataMapper.toEntity(videoMetaDataDTO);
        videoMetaData = videoMetaDataRepository.save(videoMetaData);
        return videoMetaDataMapper.toDto(videoMetaData);
    }

    @Override
    public VideoMetaDataDTO update(VideoMetaDataDTO videoMetaDataDTO) {
        log.debug("Request to update VideoMetaData : {}", videoMetaDataDTO);
        VideoMetaData videoMetaData = videoMetaDataMapper.toEntity(videoMetaDataDTO);
        videoMetaData = videoMetaDataRepository.save(videoMetaData);
        return videoMetaDataMapper.toDto(videoMetaData);
    }

    @Override
    public Optional<VideoMetaDataDTO> partialUpdate(VideoMetaDataDTO videoMetaDataDTO) {
        log.debug("Request to partially update VideoMetaData : {}", videoMetaDataDTO);

        return videoMetaDataRepository
            .findById(videoMetaDataDTO.getId())
            .map(existingVideoMetaData -> {
                videoMetaDataMapper.partialUpdate(existingVideoMetaData, videoMetaDataDTO);

                return existingVideoMetaData;
            })
            .map(videoMetaDataRepository::save)
            .map(videoMetaDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoMetaDataDTO> findAll() {
        log.debug("Request to get all VideoMetaData");
        return videoMetaDataRepository.findAll().stream().map(videoMetaDataMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the videoMetaData where Video is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VideoMetaDataDTO> findAllWhereVideoIsNull() {
        log.debug("Request to get all videoMetaData where Video is null");
        return StreamSupport.stream(videoMetaDataRepository.findAll().spliterator(), false)
            .filter(videoMetaData -> videoMetaData.getVideo() == null)
            .map(videoMetaDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VideoMetaDataDTO> findOne(Long id) {
        log.debug("Request to get VideoMetaData : {}", id);
        return videoMetaDataRepository.findById(id).map(videoMetaDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VideoMetaData : {}", id);
        videoMetaDataRepository.deleteById(id);
    }
}
