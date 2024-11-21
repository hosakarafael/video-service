package com.rafaelhosaka.rhv.video.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private List<View> views;
    private Integer userId;
    private Date createdAt;
}
