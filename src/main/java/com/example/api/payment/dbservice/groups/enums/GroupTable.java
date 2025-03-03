package com.example.api.payment.dbservice.groups.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupTable {

    GRP_IDR("groupId"),
    GRP_NME("groupName"),
    GRP_DSC("groupDescription"),
    CUS_IDR("customerId"),
    CRE_TMP("createdTimeStamp"),
    END_TMP("endTimeStamp"),
    LST_MOD_TMP("lastModifiedTimeStamp"),
    FRM_SRT_CDE("fromSortCode"),
    FRM_ACC_NUM("fromAccountNo");

    private final String value;
}
