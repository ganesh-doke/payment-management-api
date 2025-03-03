package com.example.api.payment.dbservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    private String groupId;
    private String groupName;
    private String groupDescription;
    private String customerId;
    private Timestamp createdTimeStamp;
    private Timestamp lastModifiedTimeStamp;
    private String fromSortCode;
    private String fromAccountNo;
    private List<Payee> payees;
    private Integer payeeCount;
}
