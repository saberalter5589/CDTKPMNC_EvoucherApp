package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.response.CreateBranchResponse;
import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.EUser;
import com.evoucherapp.evoucher.entity.Partner;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.exception.UnAuthorizationException;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.repository.BranchRepository;
import com.evoucherapp.evoucher.repository.EUserRepository;
import com.evoucherapp.evoucher.repository.PartnerRepository;
import com.evoucherapp.evoucher.service.BranchService;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    EUserRepository eUserRepository;
    @Autowired
    BranchRepository branchRepository;

    @Override
    @Transactional
    public CreateBranchResponse createNewBranch(CreateBranchRequest request) {
        Long userId = request.getAuthentication().getUserId();
        EUser user = eUserRepository.findByUserId(userId);
        if(user == null || user.getUserTypeId().equals(UserType.PARTNER)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }
        Long partnerId = request.getPartnerId();
        Partner partner = partnerRepository.findByPartnerId(partnerId);
        if(partner == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "partnerId");
            throw new NoDataFoundException(messageInfo);
        }

        Branch branch = new Branch();
        branch.setBranchName(request.getBranchName());
        branch.setPhone(request.getPhone());
        branch.setAddress(request.getAddress());
        EntityDxo.preCreate(userId, user);

        branchRepository.save(branch);
        return null;
    }
}
