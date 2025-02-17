package com.example.api.payment.service.transport;

public interface TransportHandler extends Cloneable {

    void close();
    byte[] getBytes();
    String sendBytes(byte[] data);
}
