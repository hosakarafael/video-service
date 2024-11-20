package com.rafaelhosaka.rhv.video.utils;

import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.model.Video;
import org.springframework.stereotype.Component;

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
        return new VideoResponse(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getVideoUrl(),
                video.getViews().size(),
                video.getUserId(),
                null,
                video.getCreatedAt()
        );
    }
}
