package com.rafaelhosaka.rhv.video.controller;

import com.rafaelhosaka.rhv.video.dto.Response;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.dto.ViewRequest;
import com.rafaelhosaka.rhv.video.service.VideoService;
import com.rafaelhosaka.rhv.video.service.ViewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video/pb")
@RequiredArgsConstructor
public class VideoPublicController {
    private final VideoService videoService;
    private final ViewService viewService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> findAll(){
        return ResponseEntity.ok().body(videoService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> findById(@PathVariable("id") Integer id){
        try{
            return ResponseEntity.ok().body(videoService.findById(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }

    @PostMapping("/view")
    public ResponseEntity<Response> increaseViews(@RequestBody ViewRequest viewRequest){
        try{
            return ResponseEntity.ok().body(viewService.increaseViews(viewRequest));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }

    @PostMapping("/user-ids")
    public ResponseEntity findByUserIds(@RequestBody List<Integer> ids){
        try{
            return ResponseEntity.ok().body(videoService.findByUserIds(ids));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }
}
