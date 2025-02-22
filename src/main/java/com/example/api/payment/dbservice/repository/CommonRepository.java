package com.example.api.payment.dbservice.repository;

import com.example.api.payment.dbservice.constants.SqlConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true, transactionManager = "preferenceTransactionManager")
public class CommonRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CommonRepository(
            final @Qualifier("preferenceNamedJdbcTemplate")
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getList() {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("sortCode", "200000", Types.VARCHAR);
        mapSqlParameterSource.addValue("account", "12345678", Types.VARCHAR);

        List<String> list = new ArrayList<>();

        jdbcTemplate.query(
                SqlConstants.GET_LIST,
                mapSqlParameterSource,
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        list.add(rs.getString("COLUMN_NAME"));
                    }
                }
        );

        return list;
    }
}
