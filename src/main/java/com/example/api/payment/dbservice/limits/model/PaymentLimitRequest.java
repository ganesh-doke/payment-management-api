package com.example.api.payment.dbservice.limits.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLimitRequest {

    private String type;
    private PaymentLimitData attributes;
}
