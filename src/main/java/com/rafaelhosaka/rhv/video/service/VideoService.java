package com.rafaelhosaka.rhv.video.service;

import com.cloudinary.Cloudinary;
import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.ErrorCode;
import com.rafaelhosaka.rhv.video.dto.Response;
import com.rafaelhosaka.rhv.video.dto.VideoRequest;
import com.rafaelhosaka.rhv.video.dto.VideoResponse;
import com.rafaelhosaka.rhv.video.model.Visibility;
import com.rafaelhosaka.rhv.video.repository.LikeRepository;
import com.rafaelhosaka.rhv.video.repository.VideoRepository;
import com.rafaelhosaka.rhv.video.repository.ViewRepository;
import com.rafaelhosaka.rhv.video.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final Mapper mapper;
    private final UserClient userClient;
    private final JwtService jwtService;
    private final ViewRepository viewRepository;
    private final LikeRepository likeRepository;
    private final CloudinaryService cloudinaryService;
    private final ThumbnailService thumbnailService;

    public List<VideoResponse> findAll() {
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAll(sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public List<VideoResponse> findAllPublic(){
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findByVisibility(Visibility.PUBLIC,sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public Response uploadVideo(VideoRequest videoRequest) throws Exception {
        if(videoRequest.userId() == null){
            return new Response("userId cannot be null", ErrorCode.VS_USER_ID_NULL);
        }
        if(videoRequest.title() == null || videoRequest.title().isEmpty()){
            return new Response("title cannot be empty", ErrorCode.VS_TITLE_EMPTY);
        }
        if(videoRequest.title().length() > 100){
            return new Response("title max length is 100", ErrorCode.VS_TITLE_LENGTH);
        }
        if(videoRequest.description() != null && videoRequest.description().length() > 5000){
            return new Response("description max length is 5000", ErrorCode.VS_DESCRIPTION_LENGTH);
        }
        var video = mapper.toVideo(videoRequest);
        try {
            videoRepository.save(video);
            var videoUrl = cloudinaryService.upload(videoRequest.videoFile().getBytes(), "videos/" + video.getId(), "video", "video");
            var thumbnailFile = thumbnailService.extractFrameFromVideo(videoRequest.videoFile(), "00:00:00");
            var thumbnailUrl = cloudinaryService.upload(thumbnailFile, "videos/" + video.getId(), "thumbnail", "image");
            video.setVideoUrl(videoUrl);
            video.setThumbnailUrl(thumbnailUrl);
            videoRepository.save(video);
        }catch (Exception e){
            if (video.getId() != null) {
                videoRepository.deleteById(video.getId());
            }
            throw new RuntimeException("Video processing failed", e);
        }
        return new Response("Video created successfully");
    }

    public VideoResponse findById(Integer id) {
        return videoRepository.findById(id)
                .map(mapper::toVideoResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+id+" not found")
                );
    }

    public VideoResponse findByIdAndPublic(Integer id) {
        return videoRepository.findByIdAndVisibility(id,Visibility.PUBLIC)
                .map(mapper::toVideoResponse)
                .orElseThrow(
                () -> new EntityNotFoundException("Video with ID "+id+" not found")
        );
    }

    public List<VideoResponse> findByUserIdsAndPublic(List<Integer> ids){
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAllByUserIdInAndVisibility(ids, Visibility.PUBLIC ,sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public List<VideoResponse> findAllByUserId(Integer id) {
        var sort = Sort.by(Sort.Order.desc("createdAt"));
        return videoRepository.findAllByUserId(id ,sort).stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    public Response deleteVideo(Integer videoId, String authHeader) throws Exception {
        var video = videoRepository.findById(videoId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+videoId+" not found")
                );

        var user = userClient.findById(video.getUserId());

        if(user.getBody() == null){
            throw new Exception("user client body is null");
        }

        if(!jwtService.isSameSubject(authHeader, user.getBody().getEmail())){
            return new Response("Requested user is not allowed to do this operation", ErrorCode.VS_FORBIDDEN_SUBJECT);
        }
        cloudinaryService.delete("videos/"+video.getId()+"/thumbnail", "image");
        cloudinaryService.delete("videos/"+video.getId()+"/video", "video");
        cloudinaryService.deleteFolder("videos/"+video.getId());
        likeRepository.deleteAll(video.getLikes());
        viewRepository.deleteAll(video.getViews());
        videoRepository.delete(video);
        return new Response("Deleted video successfully");
    }

    public Response editVideo(VideoRequest videoRequest, String authHeader) throws Exception {
        var video = videoRepository.findById(videoRequest.id())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+videoRequest.id()+" not found")
                );

        var user = userClient.findById(video.getUserId());

        if(user.getBody() == null){
            throw new Exception("user client body is null");
        }

        if(!jwtService.isSameSubject(authHeader, user.getBody().getEmail())){
            return new Response("Requested user is not allowed to do this operation", ErrorCode.VS_FORBIDDEN_SUBJECT);
        }

        if(videoRequest.title() != null){
            if(videoRequest.title().isEmpty()){
                return new Response("title cannot be empty", ErrorCode.VS_TITLE_EMPTY);
            }if(videoRequest.title().length() > 100){
                return new Response("title max length is 100", ErrorCode.VS_TITLE_LENGTH);
            }
            video.setTitle(videoRequest.title());
        }
        if(videoRequest.description() != null){
            if(videoRequest.description().length() > 5000){
                return new Response("description max length is 5000", ErrorCode.VS_DESCRIPTION_LENGTH);
            }
            video.setDescription(videoRequest.description());
        }
        if(videoRequest.visibility() != null){
            if(video.getVisibility() != videoRequest.visibility()){
                if(videoRequest.visibility() == Visibility.PRIVATE){
                    var thumbnailUrl = cloudinaryService.makeAuthenticated("videos/"+video.getId()+"/thumbnail", "image");
                    var videoUrl = cloudinaryService.makeAuthenticated("videos/"+video.getId()+"/video", "video");
                    video.setVideoUrl(videoUrl);
                    video.setThumbnailUrl(thumbnailUrl);
                }
                if(videoRequest.visibility() == Visibility.PUBLIC){
                    var thumbnailUrl = cloudinaryService.makePublic("videos/"+video.getId()+"/thumbnail", "image");
                    var videoUrl = cloudinaryService.makePublic("videos/"+video.getId()+"/video" , "video");
                    video.setVideoUrl(videoUrl);
                    video.setThumbnailUrl(thumbnailUrl);
                }

            }
            video.setVisibility(videoRequest.visibility());
        }

        videoRepository.save(video);
        return new Response("Edit video successfully");
    }

    public List<VideoResponse> search(String query) {
        return videoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
                .stream().map(mapper::toVideoResponse).toList();
    }
}
