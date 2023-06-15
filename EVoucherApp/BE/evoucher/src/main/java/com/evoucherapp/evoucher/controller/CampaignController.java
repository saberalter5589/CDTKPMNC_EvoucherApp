package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
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
@CrossOrigin(origins = "*")
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

    @DeleteMapping("/campaign/{id}")
    public ResponseEntity<SuccessResponse> deleteCampaign(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @PathVariable("id") Long id){
        BaseRequest request = new BaseRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        campaignService.deleteCampaign(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @GetMapping("/campaign/search")
    public ResponseEntity<SearchCampaignResponse> searchCampaign(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "campaignId", required = false) Long campaignId,
            @RequestParam(value="campaignCode", required = false) String campaignCode,
            @RequestParam(value="campaignName", required = false) String campaignName,
            @RequestParam(value="dateStart", required = false) String dateStart,
            @RequestParam(value="dateEnd", required = false) String dateEnd,
            @RequestParam(value="status", required = false) Long status,
            @RequestParam(value="partnerId", required = false) Long partnerId){
        SearchCampaignRequest request = new SearchCampaignRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setCampainId(campaignId);
        request.setCampaignCode(campaignCode);
        request.setCampainName(campaignName);
        request.setDateStart(dateStart);
        request.setDateEnd(dateEnd);
        request.setStatus(status);
        request.setPartnerId(partnerId);
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER, UserType.CUSTOMER));
        SearchCampaignResponse response = campaignService.searchCampaign(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
