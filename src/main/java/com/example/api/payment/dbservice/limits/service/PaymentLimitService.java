package com.example.api.payment.dbservice.limits.service;

import com.example.api.payment.dbservice.limits.model.PaymentLimitRequest;
import com.example.api.payment.dbservice.limits.model.PaymentLimitResponse;

public interface PaymentLimitService {

    PaymentLimitResponse findDailyLimit(PaymentLimitRequest paymentLimitRequest);
    PaymentLimitResponse findTransactionLimit(PaymentLimitRequest paymentLimitRequest);
    PaymentLimitResponse updateDailyLimit(PaymentLimitRequest paymentLimitRequest);
}
