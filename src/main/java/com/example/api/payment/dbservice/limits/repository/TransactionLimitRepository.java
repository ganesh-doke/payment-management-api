package com.example.api.payment.dbservice.limits.repository;

import com.example.api.payment.dbservice.limits.constants.SqlConstants;
import com.example.api.payment.dbservice.limits.enums.LimitTable;
import com.example.api.payment.dbservice.limits.mapper.PaymentLimitMapper;
import com.example.api.payment.dbservice.limits.model.PaymentLimitData;
import com.example.api.payment.dbservice.limits.model.PaymentLimitRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional(readOnly = true, transactionManager = "sessionTransactionManager")
public class TransactionLimitRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PaymentLimitMapper paymentLimitMapper;

    public TransactionLimitRepository(final @Qualifier("sessionNamedJdbcTemplate")
                            NamedParameterJdbcTemplate jdbcTemplate,
                                      PaymentLimitMapper paymentLimitMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentLimitMapper = paymentLimitMapper;
    }

    public List<PaymentLimitData> find(PaymentLimitRequest paymentLimitRequest) {

        return  jdbcTemplate.query(
                SqlConstants.GET_PAYMENT_LIMITS,
                paymentLimitMapper.buildPaymentLimitSource(paymentLimitRequest),
                (rs, rowNum) ->
                        PaymentLimitData.builder()
                                .maximumTransactionLimit(new BigDecimal(Float.toString(rs.getFloat(LimitTable.LIMIT_MAX_VAL.name()))))
                                .minimumTransactionLimit(new BigDecimal(Float.toString(rs.getFloat(LimitTable.LIMIT_MIN_VAL.name()))))
                                .maximumDailyLimit(new BigDecimal(Float.toString(rs.getFloat(LimitTable.LIMIT_DAILY_VAL.name()))))
                                .transactionType(rs.getString(LimitTable.LIMIT_TYPE.name()))
                                .build()
        );
    }
}
