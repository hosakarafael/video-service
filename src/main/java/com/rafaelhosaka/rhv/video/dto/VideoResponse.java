package com.rafaelhosaka.rhv.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Integer userId;
    private UserResponse user;
    private Date createdAt;
    private Set<Integer> likedUsers;
}
