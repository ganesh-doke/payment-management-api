package com.example.api.payment.dbservice.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SqlConstants {

    public static final String GET_LIST =
            "SELECT * FROM TABLE WHERE " +
                    "SORT_CODE = :sortCode AND " +
                    "ACCOUNT = :account";

    public static final String INSERT_QUERY =
            "INSERT INTO TABLE (SORT_CODE, ACCOUNT) " +
                    "VALUES (:sortCode, :account)";

    public static final String UPDATE_QUERY =
            "UPDATE TABLE " +
                    "SET SORT_CODE = :sortCode, ACCOUNT = :account, " +
                    "LAST_MOD_TMP = :lastModifiedTimeStamp " +
                    "WHERE END_TMP IS NUL";
}
