package com.example.api.payment.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum ErrorCodes {

    GENERAL_ERROR("Something's gone wrong. Techncial error."),
    LOWER_VERSION_TRADE("Trade with higher version is already processed."),
    EXPIRED_TRADE("Trade is expired.");

    private final String message;

    public void buildException(Exception ex) {
        log.error("{} {}", this.name(), ex);
    }
}
