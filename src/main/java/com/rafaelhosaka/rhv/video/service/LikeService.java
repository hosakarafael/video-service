package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.LikeRequest;
import com.rafaelhosaka.rhv.video.dto.Response;
import com.rafaelhosaka.rhv.video.model.Like;
import com.rafaelhosaka.rhv.video.repository.LikeRepository;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final VideoRepository videoRepository;
    private final UserClient userClient;

    public Response like(LikeRequest likeRequest) {
        var user = userClient.findById(likeRequest.userId());

        var userId = user.getBody().getId();
        var video =  videoRepository.findById(likeRequest.videoId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+likeRequest.videoId()+" not found")
                );
        if(video.getLikes().stream().anyMatch(likes -> likes.getUserId().equals(userId))){
            return new Response("this user already liked this video");
        }
        var like = new Like(userId,video.getId());
        likeRepository.save(like);
        return new Response("Liked successfully");
    }

    public Response unlike(LikeRequest likeRequest) {
        var user = userClient.findById(likeRequest.userId());

        var userId = user.getBody().getId();
        var video =  videoRepository.findById(likeRequest.videoId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+likeRequest.videoId()+" not found")
                );
        if(video.getLikes().stream().noneMatch(likes -> likes.getUserId().equals(userId))){
            return new Response("this user did not liked this video");
        }
        var like = new Like(userId,video.getId());
        likeRepository.delete(like);
        return new Response("Unliked successfully");
    }
}
