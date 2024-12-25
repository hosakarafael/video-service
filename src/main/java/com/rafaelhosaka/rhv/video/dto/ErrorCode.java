package com.rafaelhosaka.rhv.video.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    VS_SUCCESS("VS000"),
    VS_ENTITY_NOT_FOUND("VS001"),
    VS_FORBIDDEN_SUBJECT("VS002"),
    VS_USER_ID_NULL("VS003"),
    VS_TITLE_EMPTY("VS004"),
    VS_TITLE_LENGTH("VS005"),
    VS_DESCRIPTION_LENGTH("VS006"),
    VS_EXCEPTION("VS100");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        return VS_SUCCESS;
    }
}
