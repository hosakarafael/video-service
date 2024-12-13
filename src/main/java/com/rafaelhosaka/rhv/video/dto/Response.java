package com.rafaelhosaka.rhv.video.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message = "";
    private ErrorCode errorCode = ErrorCode.DEFAULT;

    public Response(String message){
        this.message = message;
    }
}
