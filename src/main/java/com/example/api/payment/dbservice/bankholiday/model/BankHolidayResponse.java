package com.example.api.payment.dbservice.bankholiday.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankHolidayResponse {

    private String type;
    private List<String> holidays;
}
