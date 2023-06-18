package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.CampaignStatus;
import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.VoucherDto;
import com.evoucherapp.evoucher.dto.obj.VoucherTemplateDto;
import com.evoucherapp.evoucher.dto.request.voucher.CreateVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.UseVoucherRequest;
import com.evoucherapp.evoucher.dto.response.voucher.CreateVoucherResponse;
import com.evoucherapp.evoucher.dto.response.voucher.SearchVoucherResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.CreateVoucherTemplateResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.SearchVoucherTemplateResponse;
import com.evoucherapp.evoucher.entity.*;
import com.evoucherapp.evoucher.exception.BadRequestException;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.mapper.VoucherDxo;
import com.evoucherapp.evoucher.mapper.VoucherTemplateDxo;
import com.evoucherapp.evoucher.repository.*;
import com.evoucherapp.evoucher.service.VoucherService;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.apache.catalina.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Objects;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    VoucherTemplateRepository voucherTemplateRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CampaignCustomerRepository campaignCustomerRepository;
    @Autowired
    EUserRepository eUserRepository;

    @Override
    @Transactional
    public CreateVoucherResponse createNewVoucher(CreateVoucherRequest request) {
        Long userId = request.getAuthentication().getUserId();
        CreateVoucherResponse response = new CreateVoucherResponse();

        Campaign campaign = campaignRepository.findByCampaignId(request.getCampaignId());
        if(campaign == null || Objects.equals(campaign.getStatus(), CampaignStatus.NOT_START)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10004, "campaignId", "campaign not exist or not in status NOT_START");
            throw new BadRequestException(messageInfo);
        }

        CampaignCustomer campaignCustomer = campaignCustomerRepository.findByCampainIdAndCustomerId(request.getCampaignId(), userId);
        if(campaignCustomer == null){
            campaignCustomer = new CampaignCustomer();
            campaignCustomer.setCustomerId(userId);
            campaignCustomer.setCampainId(campaign.getCampainId());
            campaignCustomer.setIsDeleted(false);
            EntityDxo.preCreate(userId, campaignCustomer);
            campaignCustomerRepository.save(campaignCustomer);
        }

        Long randomNumber = generateRandomNumber(userId, campaign.getCampainId());
        boolean isPrime = CommonUtil.isPrimeNumber(randomNumber);
        if(isPrime){
            VoucherDto dto = new VoucherDto();
            dto.setVoucherId(-1L);
            response.setVoucher(dto);
            return response;
        }

        List<VoucherTemplate> templateList = voucherTemplateRepository.getVoucherTemplateRandom(campaign.getCampainId());
        if(CommonUtil.isNullOrEmpty(templateList)){
            VoucherDto dto = new VoucherDto();
            dto.setVoucherId(-1L);
            response.setVoucher(dto);
            return response;
        }

        VoucherTemplate voucherTemplate = null;
        for(long i = 0; i < randomNumber; i++){
            long curIndex = i % templateList.size();
            voucherTemplate = templateList.get((int) curIndex);
        }

        if(voucherTemplate == null){
            VoucherDto dto = new VoucherDto();
            dto.setVoucherId(-1L);
            response.setVoucher(dto);
            return response;
        }

        String randomCode = voucherTemplate.getVoucherTemplateCode() + "_" + RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        Voucher voucher = new Voucher();
        voucher.setCustomerId(userId);
        voucher.setVoucherTemplateId(voucherTemplate.getVoucherTemplateId());
        voucher.setVoucherCode(randomCode);
        voucher.setVoucherName(voucherTemplate.getVoucherTemplateName());
        voucher.setDescription(voucherTemplate.getDescription());
        voucher.setIsValid(true);
        voucher.setIsUsed(false);
        voucher.setIsDeleted(false);
        EntityDxo.preCreate(userId, voucher);
        voucherRepository.save(voucher);

        VoucherDto dto = new VoucherDto();
        dto.setVoucherId(voucher.getVoucherId());
        dto.setVoucherCode(voucher.getVoucherCode());
        dto.setVoucherName(voucher.getVoucherName());
        dto.setDescription(voucher.getDescription());

        response.setVoucher(dto);
        return response;
    }

    @Override
    public SearchVoucherResponse searchVoucher(SearchVoucherRequest request) {
        Long userId = request.getAuthentication().getUserId();
        EUser user = eUserRepository.findByUserId(userId);

        if(user.getUserTypeId().equals(UserType.CUSTOMER)){
            request.setCustomerId(userId);
        }

        List<Object[]> dbSearchResult = voucherRepository.searchVoucher(userId,user.getUserTypeId(), request);
        List<VoucherDto> dtoList = VoucherDxo.convertFromDbListToDtoList(dbSearchResult);
        SearchVoucherResponse response = new SearchVoucherResponse();
        response.setVoucherDtoList(dtoList);
        return response;
    }

    @Override
    @Transactional
    public void useVoucher(Long id, UseVoucherRequest request) {
        Long userId = request.getAuthentication().getUserId();
        Voucher voucher = voucherRepository.findByVoucherIdAndCustomerId(id, userId);
        if(voucher == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "voucher");
            throw new BadRequestException(messageInfo);
        }
        voucher.setIsUsed(true);
        EntityDxo.preUpdate(userId, voucher);
        voucherRepository.save(voucher);
    }

    private Long generateRandomNumber(Long userId, Long campainId){
        long unixTime = System.currentTimeMillis() / 1000L;
        long twoNum = unixTime % 100;
        return twoNum + userId + campainId;
    }
}
