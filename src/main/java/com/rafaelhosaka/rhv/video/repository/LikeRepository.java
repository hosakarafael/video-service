package com.rafaelhosaka.rhv.video.repository;

import com.rafaelhosaka.rhv.video.model.Like;
import com.rafaelhosaka.rhv.video.model.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
