package com.example.api.payment.dbservice.limits.service.impl;

import com.example.api.payment.dbservice.limits.model.PaymentLimitData;
import com.example.api.payment.dbservice.limits.model.PaymentLimitRequest;
import com.example.api.payment.dbservice.limits.model.PaymentLimitResponse;
import com.example.api.payment.dbservice.limits.repository.DailyLimitRepository;
import com.example.api.payment.dbservice.limits.repository.TransactionLimitRepository;
import com.example.api.payment.dbservice.limits.service.PaymentLimitService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class PaymentLimitServiceImpl implements PaymentLimitService {

    private final DailyLimitRepository dailyLimitRepository;
    private final TransactionLimitRepository transactionLimitRepository;

    public PaymentLimitServiceImpl(DailyLimitRepository dailyLimitRepository,
                                   TransactionLimitRepository transactionLimitRepository) {
        this.dailyLimitRepository = dailyLimitRepository;
        this.transactionLimitRepository = transactionLimitRepository;
    }

    @Override
    public PaymentLimitResponse findDailyLimit(PaymentLimitRequest paymentLimitRequest) {

        BigDecimal amount = dailyLimitRepository.find(paymentLimitRequest);

        if (Objects.isNull(amount)) {
            amount = new BigDecimal(0);
        }

        return PaymentLimitResponse.builder()
                .type(paymentLimitRequest.getType())
                .dailyLimit(PaymentLimitData.builder()
                        .consumedDailyLimit(amount)
                        .build())
                .build();
    }

    @Override
    public PaymentLimitResponse findTransactionLimit(PaymentLimitRequest paymentLimitRequest) {

        return PaymentLimitResponse.builder()
                .type(paymentLimitRequest.getType())
                .transactionLimits(transactionLimitRepository.find(paymentLimitRequest))
                .build();
    }

    @Override
    public PaymentLimitResponse updateDailyLimit(PaymentLimitRequest paymentLimitRequest) {

        Integer updateCount = dailyLimitRepository.update(paymentLimitRequest);
        if (updateCount <= 0) {
            dailyLimitRepository.insert(paymentLimitRequest);
        }

        return PaymentLimitResponse.builder()
                .type(paymentLimitRequest.getType())
                .status("success")
                .build();
    }
}
