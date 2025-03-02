package com.example.api.payment.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum ErrorCodes {

    GENERAL_ERROR("Something's gone wrong. Technical error."),
    LIMIT_ERROR("Limit data is missing.");

    private final String message;

    public void buildException(Exception ex) {
        log.error("{}", this.name(), ex);
    }
}
