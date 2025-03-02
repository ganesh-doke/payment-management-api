package com.example.api.payment.dbservice.limits.mapper;

import com.example.api.payment.dbservice.limits.enums.LimitTable;
import com.example.api.payment.dbservice.limits.model.PaymentLimitRequest;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.Types;

@Component
@NoArgsConstructor
public class PaymentLimitMapper {

    private MapSqlParameterSource buildMapSelParameterSource(PaymentLimitRequest paymentLimitRequest) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(
                LimitTable.DELIVERY_CHN.getValue(),
                paymentLimitRequest.getAttributes().getDeliveryChannel(),
                Types.VARCHAR);

        parameterSource.addValue(
                LimitTable.SUB_DELIVERY_CHN.getValue(),
                paymentLimitRequest.getAttributes().getDeliverySubChannel(),
                Types.VARCHAR);

        parameterSource.addValue(
                LimitTable.LIMIT_CURRENCY_CDE.getValue(),
                paymentLimitRequest.getAttributes().getCurrency(),
                Types.VARCHAR);

        return parameterSource;
    }

    public MapSqlParameterSource buildPaymentLimitSource(PaymentLimitRequest paymentLimitRequest) {

        MapSqlParameterSource parameterSource = buildMapSelParameterSource(paymentLimitRequest);

        parameterSource.addValue(
                LimitTable.LIMIT_TYPE.getValue(),
                paymentLimitRequest.getAttributes().getTransactionType(),
                Types.VARCHAR);

        return parameterSource;
    }

    public MapSqlParameterSource buildDailyTotalSource(PaymentLimitRequest paymentLimitRequest) {

        MapSqlParameterSource parameterSource = buildMapSelParameterSource(paymentLimitRequest);

        parameterSource.addValue(
                LimitTable.CUSTOMER_ID.getValue(),
                paymentLimitRequest.getAttributes().getCustomerId(),
                Types.VARCHAR);

        return parameterSource;
    }

    public MapSqlParameterSource buildDailyLimitAmountSource(PaymentLimitRequest paymentLimitRequest) {

        MapSqlParameterSource parameterSource = buildDailyTotalSource(paymentLimitRequest);

        parameterSource.addValue(
                LimitTable.CUSTOMER_ID.getValue(),
                paymentLimitRequest.getAttributes().getCustomerId(),
                Types.VARCHAR);

        return parameterSource;
    }
}
