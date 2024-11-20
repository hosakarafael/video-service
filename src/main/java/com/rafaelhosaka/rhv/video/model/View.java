package com.rafaelhosaka.rhv.video.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "views")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class View {
    @Id
    @Column(name = "ip")
    private String ip;
    @Column(name = "video_id")
    private Integer videoId;
}
