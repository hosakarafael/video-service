package com.rafaelhosaka.rhv.video.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    DEFAULT("VS000"),
    ENTITY_NOT_FOUND("VS001"),
    FORBIDDEN_SUBJECT("VS002"),
    EXCEPTION("VS100");

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
        return DEFAULT;
    }
}
