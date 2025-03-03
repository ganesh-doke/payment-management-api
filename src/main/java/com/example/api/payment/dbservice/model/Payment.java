package com.example.api.payment.dbservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private String sortCode;
    private String accountNo;
    private String accountShortName;
    private String createdTimeStamp;
    private String transactionType;
    private String currencyCode;
}
