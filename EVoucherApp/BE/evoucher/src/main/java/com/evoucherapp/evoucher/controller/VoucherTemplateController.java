package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.campaign.CreateCampaignRequest;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.CreateVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.CreateVoucherTypeRequest;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import com.evoucherapp.evoucher.dto.response.campaign.SearchCampaignResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.CreateVoucherTemplateResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.SearchVoucherTemplateResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.CreateVoucherTypeResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.VoucherTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class VoucherTemplateController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    VoucherTemplateService voucherTemplateService;

    @PostMapping("/voucher-template/create")
    public ResponseEntity<CreateVoucherTemplateResponse> createVoucherTemplate(@RequestBody CreateVoucherTemplateRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        CreateVoucherTemplateResponse response = voucherTemplateService.createVoucherTemplate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/voucher-template/search")
    public ResponseEntity<SearchVoucherTemplateResponse> searchVoucherTemplate(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "voucherTemplateId", required = false) Long voucherTemplateId,
            @RequestParam(value = "voucherTypeId", required = false) Long voucherTypeId,
            @RequestParam(value = "campaignId", required = false) Long campaignId,
            @RequestParam(value = "voucherTemplateCode", required = false) String voucherTemplateCode,
            @RequestParam(value = "voucherTemplateName", required = false) String voucherTemplateName,
            @RequestParam(value = "dateStart", required = false) String dateStart,
            @RequestParam(value = "dateStart", required = false) String dateEnd){
        SearchVoucherTemplateRequest request = new SearchVoucherTemplateRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setVoucherTemplateId(voucherTemplateId);
        request.setVoucherTypeId(voucherTypeId);
        request.setCampaignId(campaignId);
        request.setVoucherTemplateCode(voucherTemplateCode);
        request.setVoucherTemplateName(voucherTemplateName);
        request.setDateStart(dateStart);
        request.setDateEnd(dateEnd);

        authenticationService.validateUser(request, Arrays.asList( UserType.PARTNER));
        SearchVoucherTemplateResponse response = voucherTemplateService.searchVoucherTemplate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/voucher-template/{id}")
    public ResponseEntity<SuccessResponse> updateVoucherTemplate(@PathVariable("id") Long id, @RequestBody CreateVoucherTemplateRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        voucherTemplateService.updateVoucherTemplate(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/voucher-template/{id}")
    public ResponseEntity<SuccessResponse> deleteVoucherTemplate(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @PathVariable("id") Long id){
        BaseRequest request = new BaseRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        voucherTemplateService.deleteVoucherTemplate(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
