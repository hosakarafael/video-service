package com.rafaelhosaka.rhv.video.dto;

import com.rafaelhosaka.rhv.video.model.Visibility;

import java.util.Date;

public record VideoRequest(Integer id, String title, String description, Integer userId , String videoUrl, Date createdAt, Visibility visibility) {
}
