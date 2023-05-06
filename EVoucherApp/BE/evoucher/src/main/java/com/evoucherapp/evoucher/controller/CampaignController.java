package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.campaign.CreateCampaignRequest;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.request.user.GetPartnerListRequest;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import com.evoucherapp.evoucher.dto.response.branch.CreateBranchResponse;
import com.evoucherapp.evoucher.dto.response.campaign.CreateCampaignResponse;
import com.evoucherapp.evoucher.dto.response.campaign.SearchCampaignResponse;
import com.evoucherapp.evoucher.dto.response.user.GetPartnerListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class CampaignController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    CampaignService campaignService;

    @PostMapping("/campaign/create")
    public ResponseEntity<CreateCampaignResponse> addCampaign(@RequestBody CreateCampaignRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        CreateCampaignResponse response = campaignService.createCampaign(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/campaign/{id}")
    public ResponseEntity<SuccessResponse> updateCampaign(@PathVariable("id") Long id, @RequestBody CreateCampaignRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        campaignService.updateCampaign(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @GetMapping("/campaign/search")
    public ResponseEntity<SearchCampaignResponse> searchCampaign(@RequestBody SearchCampaignRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER, UserType.CUSTOMER));
        SearchCampaignResponse response = campaignService.searchCampaign(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}