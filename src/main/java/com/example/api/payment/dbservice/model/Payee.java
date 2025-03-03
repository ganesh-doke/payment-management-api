package com.example.api.payment.dbservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payee {

    private String groupId;
    private String payeeId;
    private String customerId;
    private String toSortCode;
    private String toAccountNo;
    private String payeeName;
    private String payeeReference;
    private String isNewPayee;
    private Timestamp createdTimeStamp;
    private Timestamp lastModifiedTimeStamp;
    private String fromSortCode;
    private String fromAccountNo;
    private BigDecimal lastPaymentAmount;
    private BigDecimal currentPaymentAmount;
    private String action;
    private byte[] hmac;
}
