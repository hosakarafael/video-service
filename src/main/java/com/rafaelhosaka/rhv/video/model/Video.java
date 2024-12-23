package com.rafaelhosaka.rhv.video.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "videos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(length = 3000)
    private String description;
    private String videoUrl;
    @OneToMany
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Set<View> views = new HashSet<>();
    private Integer userId;
    private Date createdAt;
    @OneToMany
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Set<Like> likes = new HashSet<>();
    @OneToMany
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();
    private Visibility visibility;
}
