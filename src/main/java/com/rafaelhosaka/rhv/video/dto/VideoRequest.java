package com.rafaelhosaka.rhv.video.dto;

import java.util.Date;

public record VideoRequest(Integer id, String title, String url, int views, Date createdAt) {
}
