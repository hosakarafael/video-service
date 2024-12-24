package com.rafaelhosaka.rhv.video.controller;

import com.rafaelhosaka.rhv.video.dto.ErrorCode;
import com.rafaelhosaka.rhv.video.dto.Response;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.dto.ViewRequest;
import com.rafaelhosaka.rhv.video.service.VideoService;
import com.rafaelhosaka.rhv.video.service.ViewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<VideoResponse>> findAllPublic(){
        return ResponseEntity.ok().body(videoService.findAllPublic());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> findByIdAndPublic(@PathVariable("id") Integer id){
        try {
            return ResponseEntity.ok().body(videoService.findByIdAndPublic(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.VS_ENTITY_NOT_FOUND));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage(), ErrorCode.VS_EXCEPTION));
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
    public ResponseEntity findByUserIdsAndPublic(@RequestBody List<Integer> ids){
        try{
            return ResponseEntity.ok().body(videoService.findByUserIdsAndPublic(ids));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response(e.getMessage()));
        }
    }
}
