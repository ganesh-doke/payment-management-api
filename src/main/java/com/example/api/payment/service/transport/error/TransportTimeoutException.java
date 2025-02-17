package com.example.api.payment.service.transport.error;

public class TransportTimeoutException extends RuntimeException {

    public TransportTimeoutException(String message) { super(message); }
    public TransportTimeoutException(String message, Throwable cause) { super(message, cause); }
}
