package com.rafaelhosaka.rhv.video.repository;

import com.rafaelhosaka.rhv.video.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
