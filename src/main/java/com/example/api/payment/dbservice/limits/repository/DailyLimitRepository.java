package com.example.api.payment.dbservice.limits.repository;

import com.example.api.payment.dbservice.limits.constants.SqlConstants;
import com.example.api.payment.dbservice.limits.mapper.PaymentLimitMapper;
import com.example.api.payment.dbservice.limits.model.PaymentLimitRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
@Transactional(readOnly = true, transactionManager = "preferenceTransactionManager")
public class DailyLimitRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PaymentLimitMapper paymentLimitMapper;

    public DailyLimitRepository(@Qualifier("preferenceNamedJdbcTemplate")
                                     NamedParameterJdbcTemplate jdbcTemplate,
                                PaymentLimitMapper paymentLimitMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentLimitMapper = paymentLimitMapper;
    }

    public BigDecimal find(PaymentLimitRequest paymentLimitRequest) {

        return jdbcTemplate.queryForObject(
                SqlConstants.GET_DAILY_LIMIT,
                paymentLimitMapper.buildDailyTotalSource(paymentLimitRequest),
                BigDecimal.class);
    }

    public Integer update(PaymentLimitRequest paymentLimitRequest) {

        return jdbcTemplate.update(
                SqlConstants.UPDATE_DAILY_LIMIT,
                paymentLimitMapper.buildDailyLimitAmountSource(paymentLimitRequest)
        );
    }

    public Integer insert(PaymentLimitRequest paymentLimitRequest) {

        return jdbcTemplate.update(
                SqlConstants.INSERT_NEW_ENTRY,
                paymentLimitMapper.buildDailyLimitAmountSource(paymentLimitRequest)
        );
    }
}
