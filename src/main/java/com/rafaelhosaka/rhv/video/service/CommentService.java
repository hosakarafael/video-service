package com.rafaelhosaka.rhv.video.service;

import com.rafaelhosaka.rhv.video.client.UserClient;
import com.rafaelhosaka.rhv.video.dto.CommentRequest;
import com.rafaelhosaka.rhv.video.dto.ErrorCode;
import com.rafaelhosaka.rhv.video.dto.Response;
import com.rafaelhosaka.rhv.video.repository.CommentRepository;
import com.rafaelhosaka.rhv.video.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtService jwtService;
    private final UserClient userClient;
    private final Mapper mapper;

    public Response createComment(CommentRequest commentRequest) {
        var comment = mapper.toComment(commentRequest);
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);
        return mapper.toCommentResponse(comment);
    }

    public Response deleteComment(Integer id, String authHeader) throws Exception {
        var comment = commentRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Comment with ID "+id+" not found")
                );

        var user = userClient.findById(comment.getUserId());

        if(user.getBody() == null){
            throw new Exception("user body is null");
        }

        if(!jwtService.isSameSubject(authHeader, user.getBody().getEmail())){
           return new Response("Requested user is not allowed to do this operation", ErrorCode.VS_FORBIDDEN_SUBJECT);
        }

        commentRepository.delete(comment);
        return new Response("Deleted comment successfully");
    }
}
