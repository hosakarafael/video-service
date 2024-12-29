package com.rafaelhosaka.rhv.video.dto;

import com.rafaelhosaka.rhv.video.model.Visibility;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record VideoRequest(Integer id, String title, String description, Integer userId , MultipartFile videoFile, Date createdAt, Visibility visibility) {
}
