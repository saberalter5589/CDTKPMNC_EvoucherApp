package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.CampaignStatus;
import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.VoucherDto;
import com.evoucherapp.evoucher.dto.obj.VoucherTemplateDto;
import com.evoucherapp.evoucher.dto.request.voucher.CreateVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;
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

        // TODO: Generate random number and based on that number to get voucher template
        Campaign campaign = campaignRepository.findByCampaignId(request.getCampaignId());
        if(campaign == null || !Objects.equals(campaign.getStatus(), CampaignStatus.NOT_START)){
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

        List<VoucherTemplate> templateList = voucherTemplateRepository.findByCampainId(campaign.getCampainId());
        if(CommonUtil.isNullOrEmpty(templateList)){
            VoucherDto dto = new VoucherDto();
            dto.setVoucherId(-1L);
            response.setVoucher(dto);
            return response;
        }

        // TODO: temporarily get first template
        // TODO: if max exceed max mount, return
        VoucherTemplate voucherTemplate = templateList.get(0);
        String randomCode = voucherTemplate.getVoucherTemplateCode() + "_" + RandomStringUtils.randomAlphanumeric(10);

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
        System.out.println("SimpleEmail Start");

        String smtpHostServer = "localhost";
        String emailID = "trantrungnghia5589@gmail.com";

        Properties props = System.getProperties();

        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.port", "8025");
        props.put("mail.smtp.auth", "false");

        Session session = Session.getInstance(props, null);

        sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");

//        Long userId = request.getAuthentication().getUserId();
//        EUser user = eUserRepository.findByUserId(userId);
//        Long userType = user.getUserTypeId();
//        if(userType.equals(UserType.CUSTOMER)){
//            request.setCustomerId(userId);
//        }
//        List<Object[]> dbSearchResult = voucherRepository.searchVoucher(userId, userType, request);
//        List<VoucherDto> dtoList = VoucherDxo.convertFromDbListToDtoList(dbSearchResult);
//        SearchVoucherResponse response = new SearchVoucherResponse();
//        response.setVoucherDtoList(dtoList);
        return null;
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
