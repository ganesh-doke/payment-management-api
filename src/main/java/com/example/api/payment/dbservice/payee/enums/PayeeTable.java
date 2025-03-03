package com.example.api.payment.dbservice.payee.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayeeTable {

    GRP_IDR("groupId"),
    GRP_BEN_IDR("payeeId"),
    CUS_IDR("customerId"),
    TO_SRT_CDE("toSortCode"),
    TO_ACC_NUM("toAccountNo"),
    TO_REF("payeeReference"),
    TO_NME("payeeName"),
    IS_NEW("isNewPayee"),
    CRE_TMP("createdTimeStamp"),
    END_TMP("endTimeStamp"),
    LST_MOD_TMP("lastModifiedTimeStamp"),
    FRM_SRT_CDE("fromSortCode"),
    FRM_ACC_NUM("fromAccountNo"),
    LST_PMT_AMT("lastPaymentAmount"),
    HMAC("hmac");

    private final String value;

    public String getSqlValue() {
        return ":" + this.value;
    }
}
