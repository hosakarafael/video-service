package com.rafaelhosaka.rhv.video.repository;

import com.rafaelhosaka.rhv.video.model.Video;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findAllByUserIdIn(List<Integer> ids, Sort sort);
}
