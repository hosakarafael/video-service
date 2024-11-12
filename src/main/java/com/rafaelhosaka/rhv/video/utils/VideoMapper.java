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
                .url(request.url())
                .views(request.views())
                .createdAt(request.createdAt())
                .build();
    }

    public VideoResponse toVideoResponse(Video video){
        return new VideoResponse(
                video.getId(),
                video.getTitle(),
                video.getUrl(),
                video.getViews(),
                video.getCreatedAt()
        );
    }
}
