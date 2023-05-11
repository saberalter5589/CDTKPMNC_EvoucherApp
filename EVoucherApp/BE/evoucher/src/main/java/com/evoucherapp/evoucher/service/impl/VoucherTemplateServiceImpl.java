package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.CampaignStatus;
import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.VoucherTemplateDto;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.CreateVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.CreateVoucherTemplateResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.SearchVoucherTemplateResponse;
import com.evoucherapp.evoucher.entity.BranchVoucher;
import com.evoucherapp.evoucher.entity.Campaign;
import com.evoucherapp.evoucher.entity.VoucherTemplate;
import com.evoucherapp.evoucher.entity.VoucherType;
import com.evoucherapp.evoucher.exception.BadRequestException;
import com.evoucherapp.evoucher.exception.DataExistException;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.exception.UnAuthorizationException;
import com.evoucherapp.evoucher.mapper.CampaignDxo;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.mapper.VoucherTemplateDxo;
import com.evoucherapp.evoucher.repository.*;
import com.evoucherapp.evoucher.service.VoucherTemplateService;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VoucherTemplateServiceImpl implements VoucherTemplateService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    VoucherTypeRepository voucherTypeRepository;
    @Autowired
    VoucherTemplateRepository voucherTemplateRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BranchVoucherRepository branchVoucherRepository;

    @Override
    @Transactional
    public CreateVoucherTemplateResponse createVoucherTemplate(CreateVoucherTemplateRequest request) {
        Long partnerId = request.getAuthentication().getUserId();

        Campaign campaign = campaignRepository.findByCampaignId(request.getCampaignId());
        if(campaign == null || !Objects.equals(campaign.getStatus(), CampaignStatus.NOT_START)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10004, "campaignId", "campaign not exist or not in status NOT_START");
            throw new BadRequestException(messageInfo);
        }

        if(!campaign.getPartnerId().equals(partnerId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        VoucherType voucherType = voucherTypeRepository.findByVoucherId(request.getVoucherTypeId());
        if(voucherType == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "voucherTypeId");
            throw new NoDataFoundException(messageInfo);
        }

        VoucherTemplate dupVoucherTemplate = voucherTemplateRepository.findByCode(request.getVoucherTemplateCode());
        if(dupVoucherTemplate != null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "getVoucherTemplateCode");
            throw new DataExistException(messageInfo);
        }

        VoucherTemplate voucherTemplate = new VoucherTemplate();
        voucherTemplate.setVoucherTypeId(request.getVoucherTypeId());
        voucherTemplate.setCampainId(request.getCampaignId());
        voucherTemplate.setVoucherTemplateCode(request.getVoucherTemplateCode());
        voucherTemplate.setVoucherTemplateName(request.getVoucherTemplateName());
        voucherTemplate.setAmount(request.getAmount());
        voucherTemplate.setDescription(request.getDescription());
        voucherTemplate.setNote(request.getNote());

        LocalDate dateStart = DateTimeUtil.parseStringToDate(request.getDateStart(), DateTimeFormat.DATE);
        LocalDate dateEnd = DateTimeUtil.parseStringToDate(request.getDateEnd(), DateTimeFormat.DATE);
        voucherTemplate.setDateStart(dateStart);
        voucherTemplate.setDateEnd(dateEnd);
        voucherTemplate.setIsDeleted(false);
        EntityDxo.preCreate(partnerId, voucherTemplate);
        voucherTemplateRepository.save(voucherTemplate);

        createOrUpdateBranch(partnerId, voucherTemplate.getVoucherTemplateId(), request);

        CreateVoucherTemplateResponse response = new CreateVoucherTemplateResponse();
        response.setVoucherTemplateId(voucherTemplate.getVoucherTemplateId());
        return response;
    }

    @Override
    public SearchVoucherTemplateResponse searchVoucherTemplate(SearchVoucherTemplateRequest request) {
        List<Object[]> dbSearchResult = voucherTemplateRepository.searchVoucherTemplate(request);
        List<VoucherTemplateDto> dtoList = VoucherTemplateDxo.convertFromDbListToDtoList(dbSearchResult);
        SearchVoucherTemplateResponse response = new SearchVoucherTemplateResponse();
        response.setVoucherTemplateDtoList(dtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateVoucherTemplate(Long vTemplateId, CreateVoucherTemplateRequest request) {
        Long partnerId = request.getAuthentication().getUserId();
        VoucherTemplate voucherTemplate = voucherTemplateRepository.findByVoucherTemplateId(vTemplateId);
        if(voucherTemplate == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "voucher_template_id");
            throw new NoDataFoundException(messageInfo);
        }
        Campaign campaign = campaignRepository.findByCampaignId(voucherTemplate.getCampainId());
        if(!campaign.getPartnerId().equals(partnerId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }
        if(!Objects.equals(campaign.getStatus(), CampaignStatus.NOT_START)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        String reqCode = request.getVoucherTemplateCode();
        VoucherTemplate dupVTemplate = voucherTemplateRepository.findByCode(reqCode);
        if(dupVTemplate != null && dupVTemplate != voucherTemplate){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "voucher_template_code");
            throw new DataExistException(messageInfo);
        }

        LocalDate dateStart = DateTimeUtil.parseStringToDate(request.getDateStart(), DateTimeFormat.DATE);
        LocalDate dateEnd = DateTimeUtil.parseStringToDate(request.getDateEnd(), DateTimeFormat.DATE);

        voucherTemplate.setVoucherTypeId(request.getVoucherTypeId());
        voucherTemplate.setCampainId(request.getCampaignId());
        voucherTemplate.setVoucherTemplateCode(request.getVoucherTemplateCode());
        voucherTemplate.setVoucherTemplateName(request.getVoucherTemplateName());
        voucherTemplate.setAmount(request.getAmount());
        voucherTemplate.setDescription(request.getDescription());
        voucherTemplate.setNote(request.getNote());
        voucherTemplate.setDateStart(dateStart);
        voucherTemplate.setDateEnd(dateEnd);
        EntityDxo.preUpdate(partnerId, voucherTemplate);
        voucherTemplateRepository.save(voucherTemplate);

        createOrUpdateBranch(partnerId, voucherTemplate.getVoucherTemplateId(), request);
        // TODO: Delete all dependant [voucher]
    }

    private void createOrUpdateBranch(Long userId, Long vTemplateId, CreateVoucherTemplateRequest request){
        List<Long> branchIds = request.getBranchIds();
        List<Long> dbBranchIds = branchRepository.findAllBranchIdsOfPartner(userId);
        if(branchIds.size() == 0){
            return;
        }

        if(dbBranchIds.size() == 0){
            MessageInfo messageInfo = MessageUtil.formatMessage(10004, "branchIds", "Not");
            throw new BadRequestException(messageInfo);
        }

        for(Long branchId : branchIds){
            if(!dbBranchIds.contains(branchId)){
                MessageInfo messageInfo = MessageUtil.formatMessage(10001, "branchId");
                throw new NoDataFoundException(messageInfo);
            }
        }

        List<BranchVoucher> curBranchVoucher = branchVoucherRepository.findBranchVoucherOfVoucherId(vTemplateId);
        if(!CommonUtil.isNullOrEmpty(curBranchVoucher)){
            for(BranchVoucher branchVoucher : curBranchVoucher){
                branchVoucher.setIsDeleted(true);
                EntityDxo.preUpdate(userId, branchVoucher);
            }
        }
        branchVoucherRepository.saveAll(curBranchVoucher);

        List<BranchVoucher> newBranchVouchers = new ArrayList<>();
        for(Long branchId: branchIds){
            BranchVoucher branchVoucher = new BranchVoucher();
            branchVoucher.setBranchId(branchId);
            branchVoucher.setVoucherTemplateId(vTemplateId);
            branchVoucher.setIsDeleted(false);
            EntityDxo.preCreate(userId, branchVoucher);
            newBranchVouchers.add(branchVoucher);
        }
        branchVoucherRepository.saveAll(newBranchVouchers);
    }
}
