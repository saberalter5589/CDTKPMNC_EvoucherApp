package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.BranchDto;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.request.branch.UpdateBranchRequest;
import com.evoucherapp.evoucher.dto.response.branch.CreateBranchResponse;
import com.evoucherapp.evoucher.dto.response.branch.GetBranchListResponse;
import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.EUser;
import com.evoucherapp.evoucher.entity.Partner;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.exception.UnAuthorizationException;
import com.evoucherapp.evoucher.mapper.BranchDxo;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.repository.BranchRepository;
import com.evoucherapp.evoucher.repository.EUserRepository;
import com.evoucherapp.evoucher.repository.PartnerRepository;
import com.evoucherapp.evoucher.service.BranchService;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Long userId = validatePartner(request);

        Branch branch = new Branch();
        branch.setPartnerId(userId);
        branch.setBranchName(request.getBranchName());
        branch.setPhone(request.getPhone());
        branch.setAddress(request.getAddress());
        branch.setIsDeleted(false);
        EntityDxo.preCreate(userId, branch);
        branchRepository.save(branch);

        BranchDto branchDto = new BranchDto();
        branchDto.setBranchId(branch.getBranchId());
        branchDto.setBranchName(branch.getBranchName());
        branchDto.setPhone(branch.getPhone());
        branchDto.setAddress(branch.getAddress());
        branchDto.setPartnerId(branch.getPartnerId());
        branchDto.setPartnerName(branch.getBranchName());

        CreateBranchResponse response = new CreateBranchResponse();
        response.setBranchDto(branchDto);
        return response;
    }

    @Override
    public GetBranchListResponse searchBranch(GetBranchListRequest request) {
        List<Object[]> dbBranchList = branchRepository.searchBranch(request);
        List<BranchDto> branchDtoList = BranchDxo.mapFromDbObjListToBranchDtoList(dbBranchList);
        GetBranchListResponse response = new GetBranchListResponse();
        response.setBranchList(branchDtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateBranch(Long branchId, UpdateBranchRequest request) {
        Branch branch = branchRepository.findByBranchId(branchId);
        if(branch == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "branch_id");
            throw new NoDataFoundException(messageInfo);
        }
        Long userId = validatePartner(request);
        if(!branch.getPartnerId().equals(userId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        branch.setBranchName(request.getBranchName());
        branch.setPhone(request.getPhone());
        branch.setAddress(request.getAddress());
        EntityDxo.preUpdate(userId, branch);
        branchRepository.save(branch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long branchId, BaseRequest request) {
        Branch branch = branchRepository.findByBranchId(branchId);
        if(branch == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "branch_id");
            throw new NoDataFoundException(messageInfo);
        }
        Long userId = validatePartner(request);
        if(!branch.getPartnerId().equals(userId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        branch.setIsDeleted(true);
        EntityDxo.preUpdate(userId, branch);
        branchRepository.save(branch);
    }

    private Long validatePartner(BaseRequest request){
        Long userId = request.getAuthentication().getUserId();
        EUser user = eUserRepository.findByUserId(userId);
        if(user == null || !user.getUserTypeId().equals(UserType.PARTNER)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        Partner partner = partnerRepository.findByPartnerId(userId);
        if(partner == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "partnerId");
            throw new NoDataFoundException(messageInfo);
        }
        return userId;
    }
}
