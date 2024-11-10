package com.rafaelhosaka.rhv.video.controller;

import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.exception.VideoNotFoundException;
import com.rafaelhosaka.rhv.video.model.Video;
import com.rafaelhosaka.rhv.video.service.VideoService;
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
    public ResponseEntity<List<Video>> findAll(){
        return ResponseEntity.ok().body(videoService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Video> uploadVideo(@RequestBody VideoRequest videoRequest){
        return ResponseEntity.ok().body(videoService.uploadVideo(videoRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<Video> findById(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok().body(videoService.findById(id));
        } catch (VideoNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
