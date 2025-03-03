package com.example.api.payment.dbservice.groups.repository;

import com.example.api.payment.dbservice.groups.constants.SqlConstants;
import com.example.api.payment.dbservice.groups.enums.GroupTable;
import com.example.api.payment.dbservice.groups.mapper.GroupDataMapper;
import com.example.api.payment.dbservice.model.Group;
import com.example.api.payment.dbservice.model.Payee;
import com.example.api.payment.dbservice.model.PaymentGroupRequest;
import com.example.api.payment.dbservice.payee.enums.PayeeTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true, transactionManager = "preferenceTransactionManager")
public class GroupRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GroupDataMapper groupDataMapper;

    public GroupRepository(
            final @Qualifier("preferenceNamedJdbcTemplate")
            NamedParameterJdbcTemplate jdbcTemplate,
            GroupDataMapper groupDataMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupDataMapper = groupDataMapper;
    }

    public Group find(PaymentGroupRequest paymentGroupRequest) {

        Group group = paymentGroupRequest.getAttributes().getGroups().get(0);
        List<Payee> payeeList = new ArrayList<>();

        jdbcTemplate.query(
                SqlConstants.GROUP_DETAILS,
                groupDataMapper.find(paymentGroupRequest),
                rs -> {
                    group.setGroupName(rs.getString(GroupTable.GRP_NME.name()));
                    group.setGroupDescription(rs.getString(GroupTable.GRP_DSC.name()));
                    group.setCreatedTimeStamp(rs.getTimestamp(GroupTable.CRE_TMP.name()));

                    payeeList.add(
                            Payee.builder()
                                    .payeeId(rs.getString(PayeeTable.GRP_BEN_IDR.name()))
                                    .payeeName(rs.getString(PayeeTable.TO_NME.name()))
                                    .payeeReference(rs.getString(PayeeTable.TO_REF.name()))
                                    .toSortCode(rs.getString(PayeeTable.TO_SRT_CDE.name()))
                                    .toAccountNo(rs.getString(PayeeTable.TO_ACC_NUM.name()))
                                    .isNewPayee(rs.getString(PayeeTable.IS_NEW.name()))
                                    .createdTimeStamp(rs.getTimestamp(PayeeTable.CRE_TMP.name()))
                                    .lastModifiedTimeStamp(rs.getTimestamp(PayeeTable.LST_MOD_TMP.name()))
                                    .hmac(rs.getBytes(PayeeTable.HMAC.name()))
                                    .lastPaymentAmount(
                                            StringUtils.hasLength(rs.getString(PayeeTable.LST_PMT_AMT.name()))
                                            ? rs.getBigDecimal(PayeeTable.LST_PMT_AMT.name())
                                                    : null
                                    )
                                    .build()
                    );
                }
        );

        group.setPayees(payeeList);
        group.setPayeeCount(payeeList.size());
        return group;
    }

    private String getNextGroupId() {

        return jdbcTemplate.queryForObject(
                SqlConstants.NEXT_GROUP_ID,
                new MapSqlParameterSource(),
                String.class
        );
    }

    public Integer create(PaymentGroupRequest paymentGroupRequest) {

        return jdbcTemplate.update(
                SqlConstants.INSERT_NEW_GROUP,
                groupDataMapper.create(paymentGroupRequest, getNextGroupId())
        );
    }

    public Integer update(PaymentGroupRequest paymentGroupRequest) {

        return jdbcTemplate.update(
          SqlConstants.UPDATE_GROUP,
          groupDataMapper.update(paymentGroupRequest)
        );
    }

    public Integer delete(PaymentGroupRequest paymentGroupRequest) {

        return jdbcTemplate.update(
                SqlConstants.DELETE_GROUPS,
                groupDataMapper.delete(paymentGroupRequest));
    }

    public List<Group> list(PaymentGroupRequest paymentGroupRequest) {

        return jdbcTemplate.query(
                SqlConstants.GET_GROUP_PAYEE_LIST,
                groupDataMapper.list(paymentGroupRequest),
                (rs, rowNum) ->
                        Group.builder()
                                .groupId(String.valueOf(rs.getLong(GroupTable.GRP_IDR.name())))
                                .groupName(rs.getString(GroupTable.GRP_NME.name()))
                                .groupDescription(rs.getString(GroupTable.GRP_DSC.name()))
                                .fromSortCode(rs.getString(GroupTable.FRM_SRT_CDE.name()))
                                .fromAccountNo(rs.getString(GroupTable.FRM_ACC_NUM.name()))
                                .payeeCount(rs.getInt("PAYEE_COUNT"))
                                .build()
        );
    }
}
