package com.rafaelhosaka.rhv.video.dto;

public record CommentRequest(Integer id,Integer userId, Integer videoId, String text) {
}
