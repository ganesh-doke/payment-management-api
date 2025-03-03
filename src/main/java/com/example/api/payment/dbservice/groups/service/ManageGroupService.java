package com.example.api.payment.dbservice.groups.service;

import com.example.api.payment.dbservice.model.Group;
import com.example.api.payment.dbservice.model.PaymentGroupRequest;

import java.util.List;

public interface ManageGroupService {

    Group find(PaymentGroupRequest paymentGroupRequest);
    Integer create(PaymentGroupRequest paymentGroupRequest);
    Integer update(PaymentGroupRequest paymentGroupRequest);
    Integer delete(PaymentGroupRequest paymentGroupRequest);
    List<Group> list(PaymentGroupRequest paymentGroupRequest);
}
