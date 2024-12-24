package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.model.Visibility;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import com.rafaelhosaka.rhv.video.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final Mapper mapper;
    private final UserClient userClient;

    public List<VideoResponse> findAll() {
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAll(sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public List<VideoResponse> findAllPublic(){
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findByVisibility(Visibility.PUBLIC,sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public Integer uploadVideo(VideoRequest videoRequest) throws Exception {
        if(videoRequest.userId() == null){
            throw new Exception("userId cannot be null");
        }
        var video = mapper.toVideo(videoRequest);
        video.setCreatedAt(new Date());
        return videoRepository.save(video).getId();
    }

    public VideoResponse findById(Integer id) {
        return videoRepository.findById(id)
                .map(mapper::toVideoResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+id+" not found")
                );
    }

    public VideoResponse findByIdAndPublic(Integer id) {
        return videoRepository.findByIdAndVisibility(id,Visibility.PUBLIC)
                .map(mapper::toVideoResponse)
                .orElseThrow(
                () -> new EntityNotFoundException("Video with ID "+id+" not found")
        );
    }

    public List<VideoResponse> findByUserIdsAndPublic(List<Integer> ids){
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAllByUserIdInAndVisibility(ids, Visibility.PUBLIC ,sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public List<VideoResponse> findAllByUserId(Integer id) {
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAllByUserId(id ,sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }
}
