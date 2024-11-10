package com.rafaelhosaka.rhv.video.repository;

import com.rafaelhosaka.rhv.video.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
