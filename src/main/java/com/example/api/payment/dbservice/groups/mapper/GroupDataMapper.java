package com.example.api.payment.dbservice.groups.mapper;

import com.example.api.payment.dbservice.groups.enums.GroupTable;
import com.example.api.payment.dbservice.model.Group;
import com.example.api.payment.dbservice.model.PaymentGroupRequest;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class GroupDataMapper {

    public MapSqlParameterSource find(PaymentGroupRequest paymentGroupRequest) {

        Group group = paymentGroupRequest.getAttributes().getGroups().get(0);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue(
                GroupTable.GRP_IDR.getValue(),
                group.getGroupId(),
                Types.VARCHAR
        );
        parameterSource.addValue(
                GroupTable.FRM_SRT_CDE.getValue(),
                group.getFromSortCode(),
                Types.VARCHAR
        );
        parameterSource.addValue(
                GroupTable.FRM_ACC_NUM.getValue(),
                group.getFromAccountNo(),
                Types.VARCHAR
        );

        return parameterSource;
    }

    public MapSqlParameterSource create(PaymentGroupRequest paymentGroupRequest,
                                        String nextGroupId) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        Group group = paymentGroupRequest.getAttributes().getGroups().get(0);

        parameterSource.addValue(
                GroupTable.GRP_IDR.getValue(),
                nextGroupId,
                Types.VARCHAR);
        parameterSource.addValue(GroupTable.GRP_NME.getValue(),
                group.getGroupName(),
                Types.VARCHAR);
        parameterSource.addValue(GroupTable.FRM_SRT_CDE.getValue(),
                group.getFromSortCode(),
                Types.VARCHAR);
        parameterSource.addValue(GroupTable.FRM_ACC_NUM.getValue(),
                group.getFromAccountNo(),
                Types.VARCHAR);
        parameterSource.addValue(GroupTable.GRP_DSC.getValue(),
                group.getGroupDescription(),
                Types.VARCHAR);
        parameterSource.addValue(GroupTable.CRE_TMP.getValue(),
                Timestamp.valueOf(LocalDateTime.now()),
                Types.TIMESTAMP);

        return parameterSource;
    }

    public MapSqlParameterSource list(PaymentGroupRequest paymentGroupRequest) {

        Group group = paymentGroupRequest.getAttributes().getGroups().get(0);
        List<Object[]> sortCodeAccountNo =
                paymentGroupRequest.getAttributes()
                        .getBusinessAccounts()
                        .stream()
                        .map(t -> new Object[] {t.getSortCode(), t.getAccountNo()})
                        .collect(Collectors.toList());

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue(
                GroupTable.CUS_IDR.getValue(),
                group.getCustomerId(),
                Types.VARCHAR);
        parameterSource.addValue(
                "sortCodeAccountNo",
               sortCodeAccountNo,
                Types.VARCHAR);

        return parameterSource;
    }

    public MapSqlParameterSource update(PaymentGroupRequest paymentGroupRequest) {

        Group group = paymentGroupRequest.getAttributes().getGroups().get(0);

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(GroupTable.GRP_NME.getValue(), group.getGroupName());
        parameterSource.addValue(GroupTable.GRP_DSC.getValue(), group.getGroupDescription());
        parameterSource.addValue(GroupTable.FRM_SRT_CDE.getValue(), group.getFromSortCode());
        parameterSource.addValue(GroupTable.FRM_ACC_NUM.getValue(), group.getFromAccountNo());
        parameterSource.addValue(GroupTable.GRP_IDR.getValue(), group.getGroupId());

        return parameterSource;
    }

    public MapSqlParameterSource delete(PaymentGroupRequest paymentGroupRequest) {

        List<Object[]> groupIdSortCodeAccNo =
                paymentGroupRequest
                        .getAttributes()
                        .getGroups()
                        .stream()
                        .map(t -> new Object[] {t.getGroupId(), t.getFromSortCode(), t.getFromAccountNo()})
                        .collect(Collectors.toList());

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(GroupTable.END_TMP.getValue(), Timestamp.valueOf(LocalDateTime.now()));
        parameterSource.addValue("groupIdSortCodeAccNo", groupIdSortCodeAccNo);

        return parameterSource;
    }
}
