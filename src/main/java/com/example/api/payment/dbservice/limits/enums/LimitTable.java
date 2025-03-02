package com.example.api.payment.dbservice.limits.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LimitTable {

    LIMIT_TYPE("limitType"),
    LIMIT_MAX_VAL("maximumTransactionLimit"),
    LIMIT_MIN_VAL("minimumTransactionLimit"),
    LIMIT_DAILY_VAL("maximumDailyLimit"),
    DELIVERY_CHN("deliveryChannel"),
    SUB_DELIVERY_CHN("deliverySubChannel"),
    LIMIT_CURRENCY_CDE("currency"),
    CUSTOMER_ID("customerId"),
    DAILY_LIMIT_TOTAL("dailyLimitTotal");

    private final String value;
}
