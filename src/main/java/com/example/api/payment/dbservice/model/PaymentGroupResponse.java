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
public class PaymentGroupResponse {

    private String type;
    private List<Group> groups;
    private List<Payee> existingPayees;
}
