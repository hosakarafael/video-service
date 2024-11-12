package com.rafaelhosaka.rhv.video.controller;

import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> findAll(){
        return ResponseEntity.ok().body(videoService.findAll());
    }

    @PostMapping
    public ResponseEntity<Integer> uploadVideo(@RequestBody VideoRequest videoRequest){
        return ResponseEntity.ok().body(videoService.uploadVideo(videoRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<VideoResponse> findById(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(videoService.findById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
