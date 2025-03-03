package com.example.api.payment.dbservice.groups.service.impl;

import com.example.api.payment.dbservice.groups.repository.GroupRepository;
import com.example.api.payment.dbservice.groups.service.ManageGroupService;
import com.example.api.payment.dbservice.model.Group;
import com.example.api.payment.dbservice.model.Payee;
import com.example.api.payment.dbservice.model.PaymentGroupRequest;
import com.example.api.payment.dbservice.payee.repository.PayeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManageGroupServiceImpl implements ManageGroupService {

    final GroupRepository groupRepository;
    final PayeeRepository payeeRepository;

    public ManageGroupServiceImpl(GroupRepository groupRepository,
                                  PayeeRepository payeeRepository) {
        this.groupRepository = groupRepository;
        this.payeeRepository = payeeRepository;
    }

    @Override
    public Group find(PaymentGroupRequest paymentGroupRequest) {
        return groupRepository.find(paymentGroupRequest);
    }

    @Override
    public Integer create(PaymentGroupRequest paymentGroupRequest) {

        Integer createStatus = groupRepository.create(paymentGroupRequest);
        if (createStatus > 0) {
            Group group = paymentGroupRequest.getAttributes().getGroups().get(0);
            payeeRepository.create(group.getPayees());
        }

        return createStatus;
    }

    @Override
    public Integer update(PaymentGroupRequest paymentGroupRequest) {

        Integer updateStatus = groupRepository.update(paymentGroupRequest);
        if (updateStatus > 0) {
            Group group = paymentGroupRequest.getAttributes().getGroups().get(0);

            List<Payee> addPayees = new ArrayList<>();
            List<Payee> removePayees = new ArrayList<>();

            for (Payee payee : group.getPayees()) {
                if (StringUtils.hasLength(payee.getAction())) {
                    if (payee.getGroupId().equalsIgnoreCase("Add")) {
                        addPayees.add(payee);
                    } else if (payee.getGroupId().equalsIgnoreCase("Remove")) {
                        removePayees.add(payee);
                    }
                }
            }

            if (!CollectionUtils.isEmpty(addPayees)) payeeRepository.create(addPayees);
            if (!CollectionUtils.isEmpty(removePayees)) payeeRepository.remove(removePayees);
        }

        return updateStatus;
    }

    @Override
    public Integer delete(PaymentGroupRequest paymentGroupRequest) {

        return groupRepository.delete(paymentGroupRequest);
    }

    @Override
    public List<Group> list(PaymentGroupRequest paymentGroupRequest) {

        return groupRepository.list(paymentGroupRequest);
    }
}
