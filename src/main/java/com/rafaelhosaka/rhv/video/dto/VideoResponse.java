package com.rafaelhosaka.rhv.video.dto;

import java.util.Date;

public record VideoResponse(Integer id, String title, String url, int views, Date createdAt) {
}
