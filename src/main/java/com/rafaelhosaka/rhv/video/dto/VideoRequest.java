package com.rafaelhosaka.rhv.video.dto;

import java.util.Date;

public record VideoRequest(Long id, String title, String url, int views, Date createdAt) {
}
