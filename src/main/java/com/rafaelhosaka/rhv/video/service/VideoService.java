package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import com.rafaelhosaka.rhv.video.utils.VideoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoMapper mapper;

    public List<VideoResponse> findAll(){
        return videoRepository.findAll().stream().map(mapper::toVideoResponse).collect(Collectors.toList());
    }

    public Integer uploadVideo(VideoRequest videoRequest) {
        var video = mapper.toVideo(videoRequest);
        return videoRepository.save(video).getId();
    }

    public VideoResponse findById(Integer id) {
        return videoRepository.findById(id)
                .map(mapper::toVideoResponse)
                .orElseThrow(
                () -> new EntityNotFoundException("Video with ID "+id+" not found")
        );
    }
}
