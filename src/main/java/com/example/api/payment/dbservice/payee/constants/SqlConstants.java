package com.example.api.payment.dbservice.payee.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SqlConstants {

    public static final String INSERT_NEW_PAYEE =
            "INSERT INTO TO_PAYEE " +
                    "(GRP_IDR, GRP_BEN_IDR, CUS_IDR, TO_SRT_CDE, TO_ACC_NUM, TO_REF, TO_NME, IS_NEW, " +
                    "CRE_TMP, LST_MOD_TMP, FRM_SRT_CDE, FRM_ACC_NUM, HMAC) " +
                    "VALUES " +
                    "(:groupId, :payeeId, :customerId, :toSortCode, :toAccountNo, :payeeReference, " +
                    ":payeeName, :isNewPayee, :createdTimeStamp, :lastModifiedTimeStamp, " +
                    ":fromSortCode, :fromAccountNo, :hmac)";

    public static final String REMOVE_PAYEE =
            "UPDATE TO_PAYEE SET END_TMP = :endTimeStamp " +
                    "WHERE GRP_IDR = :groupId " +
                    "AND GRP_BEN_IDR = :payeeId " +
                    "AND FRM_SRT_CDE = :fromSortCode" +
                    "AND FRM_ACC_NUM = :fromAccountNo";
}
