package com.rafaelhosaka.rhv.video.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewId implements Serializable {
    private String ip;
    private Integer videoId;
}
