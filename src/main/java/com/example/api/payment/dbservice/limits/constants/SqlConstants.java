package com.example.api.payment.dbservice.limits.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SqlConstants {

    public static final String GET_PAYMENT_LIMITS =
            "SELECT LIMIT_TYPE, LIMIT_MAX_VAL, LIMIT_MIN_VAL, LIMIT_DAILY_VAL FROM TO_LIMIT " +
                    "WHERE DELIVERY_CHN = :deliveryChannel " +
                    "AND SUB_DELIVERY_CHN = :deliverySubChannel " +
                    "AND LIMIT_CURRENCY_CDE = :currency " +
                    "AND LIMIT_TYPE = :partyType";

    public static final String GET_DAILY_LIMIT =
            "SELECT SUM(DAILY_LIMIT_TOTAL) " +
                    "FROM TO_DAILY_LIMIT " +
                    "WHERE DELIVERY_CHN = :deliveryChannel " +
                    "AND SUB_DELIVERY_CHN = :deliverySubChannel " +
                    "AND LIMIT_CURRENCY_CDE = :currency " +
                    "AND CUSTOMER_ID = :customerId" +
                    "AND to_char(LIMIT_DATE, 'DD-MON-YY' = to_char(SYSDATE, 'DD-MON-YY')";

    public static final String INSERT_NEW_ENTRY =
            "INSERT INTO TO_DAILY_LIMIT " +
                    "(DELIVERY_CHN, SUB_DELIVERY_CHN, LIMIT_CURRENCY_CDE, CUSTOMER_ID, LIMIT_DATE, DAILY_LIMIT_TOTAL) " +
                    "VALUES " +
                    "(:deliveryChannel, :deliverySubChannel, :currency, :customerId, TRUNC(SYSDATE), :dailyLimitTotal)";

    public static final String UPDATE_DAILY_LIMIT =
            "UPDATE TO_DAILY_LIMIT SET DAILY_LIMIT_TOTAL = DAILY_LIMIT_TOTAL + :dailyTotal " +
                    "WHERE DELIVERY_CHN = :deliveryChannel " +
                    "AND SUB_DELIVERY_CHN = :deliverySubChannel " +
                    "AND LIMIT_CURRENCY_CDE = :currency " +
                    "AND CUSTOMER_ID = :customerId" +
                    "AND to_char(LIMIT_DATE, 'DD-MON-YY' = to_char(SYSDATE, 'DD-MON-YY')";
}
