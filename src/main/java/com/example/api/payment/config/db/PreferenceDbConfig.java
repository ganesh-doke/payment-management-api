package com.example.api.payment.config.db;

import com.example.api.payment.constants.DbConstant;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class PreferenceDbConfig {

    @Bean
    @ConfigurationProperties("app.datasource.preference")
    public DataSourceProperties preferenceDataSourceProperties() {

        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.preference.hikari")
    public HikariDataSource preferenceDataSource() {

        HikariDataSource hikariDataSource = preferenceDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        hikariDataSource.addDataSourceProperty(
                DbConstant.CACHE_PREP_STATEMENT.getKey(),
                DbConstant.CACHE_PREP_STATEMENT.getValue());
        hikariDataSource.addDataSourceProperty(
                DbConstant.PREP_STATEMENT_CACHE_SIZE.getKey(),
                DbConstant.PREP_STATEMENT_CACHE_SIZE.getValue());
        hikariDataSource.addDataSourceProperty(
                DbConstant.PREP_STATEMENT_CACHE_SQL_LIMIT.getKey(),
                DbConstant.PREP_STATEMENT_CACHE_SQL_LIMIT.getValue());

        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate preferencesJdbcTemplate(@Qualifier("preferenceDataSource") DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       jdbcTemplate.setFetchSize(Integer.parseInt(DbConstant.FETCH_SIZE.getValue()));
       return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate preferenceNamedJdbcTemplate(
            @Qualifier("preferenceDataSource") DataSource dataSource) {

        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Primary
    @Bean
    public PlatformTransactionManager preferenceTransactionMapper(
            @Qualifier("preferenceDataSource") DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
