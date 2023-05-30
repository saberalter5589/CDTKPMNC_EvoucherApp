package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.partnertype.CreatePartnerTypeRequest;
import com.evoucherapp.evoucher.dto.request.partnertype.SearchPartnerTypeRequest;
import com.evoucherapp.evoucher.dto.request.user.GetPartnerListRequest;
import com.evoucherapp.evoucher.dto.request.user.UpdateCustomerRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.CreateVoucherTypeRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.GetVoucherTypeListRequest;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import com.evoucherapp.evoucher.dto.response.partnertype.CreatePartnerTypeResponse;
import com.evoucherapp.evoucher.dto.response.partnertype.SearchPartnerTypeResponse;
import com.evoucherapp.evoucher.dto.response.user.GetPartnerListResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.CreateVoucherTypeResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.GetVoucherTypeListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.PartnerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class PartnerTypeController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    PartnerTypeService partnerTypeService;

    @PostMapping("/partner-type/create")
    public ResponseEntity<CreatePartnerTypeResponse> addPartnerType(@RequestBody CreatePartnerTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN));
        CreatePartnerTypeResponse response = partnerTypeService.createPartnerType(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/partner-type/search")
    public ResponseEntity<SearchPartnerTypeResponse> searchPartnerType(){
        SearchPartnerTypeRequest request = new SearchPartnerTypeRequest();
        SearchPartnerTypeResponse response = partnerTypeService.searchPartnerType(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/partner-type/{id}")
    public ResponseEntity<SuccessResponse> updatePartnerType(@PathVariable("id") Long id, @RequestBody CreatePartnerTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN));
        partnerTypeService.updatePartnerType(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
