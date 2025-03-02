package com.example.api.payment.mqservice.transport.error;

public class TransportTimeoutException extends RuntimeException {

    public TransportTimeoutException(String message) { super(message); }
    public TransportTimeoutException(String message, Throwable cause) { super(message, cause); }
}
