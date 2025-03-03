package com.example.api.payment.dbservice.groups.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SqlConstants {

    public static final String GROUP_DETAILS =
            "SELECT G.GRP_IDR, G.GRP_NME, G.GRP_DSC. G.CRE_TMP, G.LST_MOD_TMP, G.FRM_SRT_CDE, G.FRM_ACC_NUM, " +
                    "P.GRP_BEN_IDR, P.TO_SRT_CDE, P.TO_ACC_NUM, P.TO_NME, P.IS_NEW" +
                    "FROM TO_GROUP G " +
                    "LEFT JOIN TO_PAYEE P ON G.GRP_IDR = P.GRP_IDR " +
                    "AND G.FRM_SRT_CDE = P.FRM_SRT_CDE " +
                    "AND G.FRM_ACC_NUM = P.FRM_ACC_NUM " +
                    "WHERE G.FRM_SRT_CDE = :fromSortCode and G.FRM_ACC_NUM = :fromAccountNo " +
                    "AND G.GRP_IDR = :groupId " +
                    "AND P.END_TMP IS NULL";

    public static final String INSERT_NEW_GROUP =
            "INSERT INTO TO_GROUP " +
                    "(GRP_IDR, GRP_NME, GRP_DSC. CUS_IDR, CRE_TMP, LST_MOD_TMP, FRM_SRT_CDE, FRM_ACC_NUM) " +
                    "VALUES " +
                    "(:groupId, :groupName, :groupDescription, :customerId, :createdTimeStamp, " +
                    ":lastModifiedTimeStamp, :fromSortCode, :fromAccountNo)";

    public static final String DUPLICATE_GROUP =
            "SELECT 1 FROM TO_GROUP " +
                    "WHERE UPPER(GRP_NME) = :groupName " +
                    "AND CUS_IDR = :customerId " +
                    "AND END_TMP IS NULL";

    public static final String UPDATE_GROUP =
            "UPDATE TO_GROUP SET GRP_NME = :groupName " +
                    ", GRP_DSC = :groupDescription " +
                    ", LST_MOD_TMP = :lastModifiedTimeStamp " +
                    "WHERE GRP_IDR = :groupId " +
                    "AND FRM_SRT_CDE = :fromSortCode" +
                    "AND FRM_ACC_NUM = :fromAccountNo " +
                    "AND END_TMP IS NULL";

    public static final String DELETE_GROUPS =
            "UPDATE TO_GROUP SET END_TMP = :endTimeStamp " +
                    "WHERE (GRP_IDR, FRM_SRT_CDE, FRM_ACC_NUM) IN (:groupIdSortCodeAccNo)";

    public static final String NEXT_GROUP_ID =
            "SELECT NVL(MAX(GRP_IDR), 0) + 1 FROM TO_GROUP";

    public static final String GET_GROUP_PAYEE_LIST =
            "SELECT G.GRP_IDR, COUNT(P.GRP_BEN_SEQ) AS PAYEE_COUNT, " +
                    "G.GRP_NME, G.FRM_SRT_CDE, G.FRM_ACC_NUM " +
                    "FROM TO_GROUP G, " +
                    "LEFT JOIN TO_PAYEE P ON G.GRP_IDR = P.GRP_IDR " +
                    "AND G.FRM_SRT_CDE = P.FRM_SRT_CDE " +
                    "AND G.FRM_ACC_NUM = P.FRM_ACC_NUM " +
                    "WHERE G.END_TMP IS NULL AND P.END_TMP IS NULL " +
                    "(G.CUS_IDR = :customerId " +
                    "OR ( G.CUS_IDR IS NULL " +
                    "AND (G.FRM_SRT_CDE, G.FRM_ACC_NUM) IN (:sortCodeAccNo))";
}
