package com.example.api.payment.mqservice.transport;

public interface TransportHandler extends Cloneable {

    void close();
    byte[] getBytes();
    String sendBytes(byte[] data);
}
