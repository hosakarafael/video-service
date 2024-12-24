package com.rafaelhosaka.rhv.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message = "";
    private ErrorCode errorCode = ErrorCode.VS_SUCCESS;

    public Response(String message){
        this.message = message;
    }
}
