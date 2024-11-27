package com.rafaelhosaka.rhv.video.repository;

import com.rafaelhosaka.rhv.video.model.View;
import com.rafaelhosaka.rhv.video.model.ViewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, ViewId> {
}
