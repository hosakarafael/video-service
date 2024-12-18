package com.rafaelhosaka.rhv.video.controller;

import com.rafaelhosaka.rhv.video.dto.*;
import com.rafaelhosaka.rhv.video.service.CommentService;
import com.rafaelhosaka.rhv.video.service.JwtService;
import com.rafaelhosaka.rhv.video.service.LikeService;
import com.rafaelhosaka.rhv.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Integer> uploadVideo(@RequestBody VideoRequest videoRequest){
        try{
            return ResponseEntity.ok().body(videoService.uploadVideo(videoRequest));
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<Response> like(@RequestBody LikeRequest likeRequest){
        try {
            return ResponseEntity.ok(likeService.like(likeRequest));
        }catch (EntityNotFoundException e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.ENTITY_NOT_FOUND));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.EXCEPTION));
        }

    }

    @PostMapping("/unlike")
    public ResponseEntity<Response> unlike(@RequestBody LikeRequest likeRequest){
        try {
            return ResponseEntity.ok(likeService.unlike(likeRequest));
        }catch (EntityNotFoundException e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.ENTITY_NOT_FOUND));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.EXCEPTION));
        }

    }

    @PostMapping("/comment")
    public ResponseEntity<Response> createComment(@RequestBody CommentRequest commentRequest){
        try {
            return ResponseEntity.ok(commentService.createComment(commentRequest));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.EXCEPTION));
        }
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable("id") Integer id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        try {
            return ResponseEntity.ok(commentService.deleteComment(id, authHeader));
        }catch (EntityNotFoundException e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.ENTITY_NOT_FOUND));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.EXCEPTION));
        }
    }
}
