package com.example.api.payment.dbservice.dbconfig;

import com.example.api.payment.dbservice.constants.DbConstant;
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
public class SessionDbConfig {

    @Bean
    @ConfigurationProperties("app.datasource.session")
    public DataSourceProperties sessionDataSourceProperties() {

        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.session.hikari")
    public HikariDataSource sessionDataSource() {

        HikariDataSource hikariDataSource = sessionDataSourceProperties()
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
    public JdbcTemplate sessionsJdbcTemplate(@Qualifier("sessionDataSource") DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
       jdbcTemplate.setFetchSize(Integer.parseInt(DbConstant.FETCH_SIZE.getValue()));
       return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate sessionNamedJdbcTemplate(
            @Qualifier("sessionDataSource") DataSource dataSource) {

        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Primary
    @Bean
    public PlatformTransactionManager sessionTransactionManager(
            @Qualifier("sessionDataSource") DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
