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
public class PaymentGroupRequest {

    private String type;
    private PaymentGroupData attributes;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentGroupData {

        private List<Group> groups;
        private List<BusinessAccount> businessAccounts;
        private Payee newPayee;
        private Payment payment;
    }
}
