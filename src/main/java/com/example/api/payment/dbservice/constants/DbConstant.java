package com.example.api.payment.dbservice.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DbConstant {

    CACHE_PREP_STATEMENT("cachePrepStmts", "true"),
    PREP_STATEMENT_CACHE_SIZE("prepStmtCacheSize", "250"),
    PREP_STATEMENT_CACHE_SQL_LIMIT("prepStmtCacheSqlLimit", "2048"),
    FETCH_SIZE("fetchSize", "10000");

    private final String key;
    private final String value;
}
