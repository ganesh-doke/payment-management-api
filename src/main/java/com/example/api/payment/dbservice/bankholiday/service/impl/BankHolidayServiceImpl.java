package com.example.api.payment.dbservice.bankholiday.service.impl;

import com.example.api.payment.dbservice.bankholiday.model.BankHolidayResponse;
import com.example.api.payment.dbservice.bankholiday.repository.BankHolidayRepository;
import com.example.api.payment.dbservice.bankholiday.service.BankHolidayService;
import org.springframework.stereotype.Service;

@Service
public class BankHolidayServiceImpl implements BankHolidayService {

    private final BankHolidayRepository bankHolidayRepository;

    public BankHolidayServiceImpl(BankHolidayRepository bankHolidayRepository) {
        this.bankHolidayRepository = bankHolidayRepository;
    }

    @Override
    public BankHolidayResponse getBankHolidays() {

        return BankHolidayResponse.builder()
                .type("bankHolidays")
                .holidays(bankHolidayRepository.find())
                .build();
    }
}
