package com.example.api.payment.dbservice.payee.repository;

import com.example.api.payment.dbservice.model.Payee;
import com.example.api.payment.dbservice.payee.constants.SqlConstants;
import com.example.api.payment.dbservice.payee.enums.PayeeTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true, transactionManager = "preferenceTransactionManager")
public class PayeeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PayeeRepository(
            final @Qualifier("preferenceNamedJdbcTemplate")
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(List<Payee> payees) {

        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[payees.size()];

        int count = 0;
        for (Payee payee : payees) {

            SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource()
                            .addValue(PayeeTable.GRP_IDR.getValue(), payee.getGroupId())
                            .addValue(PayeeTable.FRM_SRT_CDE.getValue(), payee.getFromSortCode())
                            .addValue(PayeeTable.FRM_ACC_NUM.getValue(), payee.getFromAccountNo())
                            .addValue(PayeeTable.GRP_BEN_IDR.getValue(), payee.getPayeeId())
                            .addValue(PayeeTable.CRE_TMP.getValue(), payee.getCreatedTimeStamp(), Types.TIMESTAMP)
                            .addValue(PayeeTable.TO_NME.getValue(), payee.getPayeeName())
                            .addValue(PayeeTable.TO_REF.getValue(), payee.getPayeeReference())
                            .addValue(PayeeTable.TO_SRT_CDE.getValue(), payee.getToSortCode())
                            .addValue(PayeeTable.TO_ACC_NUM.getValue(), payee.getToAccountNo())
                            .addValue(PayeeTable.HMAC.getValue(), payee.getHmac());

            sqlParameterSources[count++] = sqlParameterSource;
        }

        jdbcTemplate.batchUpdate(SqlConstants.INSERT_NEW_PAYEE, sqlParameterSources);
    }

    public void remove(List<Payee> payees) {

        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[payees.size()];

        int count = 0;
        for (Payee payee : payees) {

            SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource()
                            .addValue(PayeeTable.GRP_IDR.getValue(), payee.getGroupId())
                            .addValue(PayeeTable.FRM_SRT_CDE.getValue(), payee.getFromSortCode())
                            .addValue(PayeeTable.FRM_ACC_NUM.getValue(), payee.getFromAccountNo())
                            .addValue(PayeeTable.GRP_BEN_IDR.getValue(), payee.getPayeeId())
                            .addValue(PayeeTable.END_TMP.getValue(), Timestamp.valueOf(LocalDateTime.now()), Types.TIMESTAMP);

            sqlParameterSources[count++] = sqlParameterSource;
        }

        jdbcTemplate.batchUpdate(SqlConstants.REMOVE_PAYEE, sqlParameterSources);
    }
}
