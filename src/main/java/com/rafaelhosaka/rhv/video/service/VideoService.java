package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.exception.VideoNotFoundException;
import com.rafaelhosaka.rhv.video.model.Video;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;

    public List<Video> findAll(){
        return videoRepository.findAll();
    }

    public Video uploadVideo(VideoRequest videoRequest) {
        var video = Video.builder()
                .title(videoRequest.title())
                .createdAt(new Date())
                .build();
        videoRepository.save(video);
        return video;
    }

    public Video findById(Long id) throws VideoNotFoundException {
        return videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException("Video with ID "+id+" not found")
        );
    }
}
