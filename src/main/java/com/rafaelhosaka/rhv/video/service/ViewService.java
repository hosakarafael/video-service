package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.dto.ViewRequest;
import com.rafaelhosaka.rhv.video.model.View;
import com.rafaelhosaka.rhv.video.model.ViewId;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import com.rafaelhosaka.rhv.video.repository.ViewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final ViewRepository viewRepository;
    private final VideoRepository videoRepository;

    public String increaseViews(ViewRequest viewRequest) {
        var ip = viewRequest.ip();
        var video =  videoRepository.findById(viewRequest.videoId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+viewRequest.videoId()+" not found")
                );
        if(video.getViews().stream().anyMatch(view -> view.getIp().equals(ip))){
            return "this user already watched this video";
        }
        var view = new View(ip,video.getId());
        video.getViews().add(view);
        try{
            viewRepository.save(view);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "view increase successfully";
    }
}
