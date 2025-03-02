package com.example.api.payment.dbservice.limits.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLimitResponse {

    private String type;
    private PaymentLimitData dailyLimit;
    private List<PaymentLimitData> transactionLimits;
    private String status;
}
