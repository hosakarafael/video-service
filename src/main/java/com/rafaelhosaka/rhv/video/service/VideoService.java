package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import com.rafaelhosaka.rhv.video.repository.ViewRepository;
import com.rafaelhosaka.rhv.video.utils.VideoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoMapper mapper;
    private final UserClient userClient;

    public List<VideoResponse> findAll(){
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAll(sort).stream()
                .map(mapper::toVideoResponse)
                .peek(result -> {
                    var userResponse = userClient.findById(result.getUserId());
                    result.setUser(userResponse.getBody());
                }).toList();
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
        var videoResponse =  videoRepository.findById(id)
                .map(mapper::toVideoResponse)
                .orElseThrow(
                () -> new EntityNotFoundException("Video with ID "+id+" not found")
        );
        var userResponse = userClient.findById(videoResponse.getUserId());
        videoResponse.setUser(userResponse.getBody());
        return videoResponse;
    }

    public List<VideoResponse> findByUserIds(List<Integer> ids){
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAllByUserIdIn(ids, sort).stream()
                .map(mapper::toVideoResponse)
                .peek(result -> {
                    var userResponse = userClient.findById(result.getUserId());
                    result.setUser(userResponse.getBody());
                }).toList();
    }
}
