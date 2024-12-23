package com.rafaelhosaka.rhv.video.dto;

import com.rafaelhosaka.rhv.video.model.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoResponse extends Response{
    private Integer id;
    private String title;
    private String description;
    private String videoUrl;
    private int views;
    private UserResponse user;
    private Date createdAt;
    private Set<Integer> likedUsers;
    private List<CommentResponse> comments = new ArrayList<>();
    private Visibility visibility;
}
