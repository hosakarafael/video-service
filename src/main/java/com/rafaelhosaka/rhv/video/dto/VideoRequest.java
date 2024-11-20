package com.rafaelhosaka.rhv.video.dto;

import java.util.Date;

public record VideoRequest(Integer id, String title, String description, Integer userId , String videoUrl, Date createdAt) {
}
