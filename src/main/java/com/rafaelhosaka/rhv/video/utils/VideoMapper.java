package com.rafaelhosaka.rhv.video.utils;

import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.model.Like;
import com.rafaelhosaka.rhv.video.model.Video;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VideoMapper {
    public Video toVideo(VideoRequest request){
        return Video.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .videoUrl(request.videoUrl())
                .createdAt(request.createdAt())
                .userId(request.userId())
                .build();
    }

    public VideoResponse toVideoResponse(Video video){
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .videoUrl(video.getVideoUrl())
                .views(video.getViews().size())
                .userId(video.getUserId())
                .createdAt(video.getCreatedAt())
                .likedUsers(video.getLikes().stream().map(Like::getUserId).collect(Collectors.toSet()))
                .build();
    }
}
