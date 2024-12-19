package com.rafaelhosaka.rhv.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse extends Response{
    private Integer id;
    private UserResponse user;
    private String text;
    private Date createdAt;
}
