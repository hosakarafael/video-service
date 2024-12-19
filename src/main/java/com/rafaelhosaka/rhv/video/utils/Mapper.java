package com.rafaelhosaka.rhv.video.utils;

import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.CommentRequest;
import com.rafaelhosaka.rhv.video.dto.CommentResponse;
import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.model.Comment;
import com.rafaelhosaka.rhv.video.model.Like;
import com.rafaelhosaka.rhv.video.model.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final UserClient userClient;

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
                .createdAt(video.getCreatedAt())
                .likedUsers(video.getLikes().stream()
                        .map(Like::getUserId)
                        .collect(Collectors.toSet()))
                .comments(
                        video.getComments().stream()
                                .map(this::toCommentResponse)
                                .sorted(Comparator.comparing(CommentResponse::getCreatedAt).reversed())
                                .toList())
                .user(userClient.findById(video.getUserId()).getBody())
                .build();
    }

    public Comment toComment(CommentRequest request){
        return Comment.builder()
                .id(request.id())
                .userId(request.userId())
                .videoId(request.videoId())
                .text(request.text())
                .build();
    }

    public CommentResponse toCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .user(userClient.findById(comment.getUserId()).getBody())
                .build();
    }
}
