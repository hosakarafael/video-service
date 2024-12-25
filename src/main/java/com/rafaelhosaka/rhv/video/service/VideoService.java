package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.ErrorCode;
import com.rafaelhosaka.rhv.video.dto.Response;
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

    public Response uploadVideo(VideoRequest videoRequest) {
        if(videoRequest.userId() == null){
            return new Response("userId cannot be null", ErrorCode.VS_USER_ID_NULL);
        }
        if(videoRequest.title().isEmpty()){
            return new Response("title cannot be empty", ErrorCode.VS_TITLE_EMPTY);
        }
        if(videoRequest.title().length() > 100){
            return new Response("title max length is 100", ErrorCode.VS_TITLE_LENGTH);
        }
        if(videoRequest.description() != null && videoRequest.description().length() > 5000){
            return new Response("description max length is 5000", ErrorCode.VS_DESCRIPTION_LENGTH);
        }
        var video = mapper.toVideo(videoRequest);
        video.setCreatedAt(new Date());
        videoRepository.save(video);
        return new Response("Video created successfully");
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
