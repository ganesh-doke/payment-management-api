package com.example.api.payment.dbservice.limits.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentLimitData {

    private BigDecimal consumedDailyLimit;
    private BigDecimal maximumDailyLimit;
    private BigDecimal maximumTransactionLimit;
    private BigDecimal minimumTransactionLimit;
    private String transactionType;
    private String customerId;

    private String currency;
    private String deliveryChannel;
    private String deliverySubChannel;
    private String paymentDate;
    private BigDecimal paymentAmount;
}
