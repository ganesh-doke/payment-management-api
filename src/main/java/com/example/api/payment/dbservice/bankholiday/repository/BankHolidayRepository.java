package com.example.api.payment.dbservice.bankholiday.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BankHolidayRepository {

    private static final String GET_BANK_HOLIDAY =
            "SELECT DISTINCT TO_CHAR(BANK_HOLIDAY, 'yyyy-MM-dd'))" +
                    " FROM HOLIDAY";

    private final JdbcTemplate jdbcTemplate;

    public BankHolidayRepository(final @Qualifier("sessionJdbcTemplate")
                                 JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getBankHolidays() {
        return jdbcTemplate.queryForList(GET_BANK_HOLIDAY, String.class);
    }
}
