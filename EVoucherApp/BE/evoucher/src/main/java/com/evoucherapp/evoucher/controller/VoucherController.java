package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.voucher.CreateVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.CreateVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.response.voucher.CreateVoucherResponse;
import com.evoucherapp.evoucher.dto.response.voucher.SearchVoucherResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.CreateVoucherTemplateResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.SearchVoucherTemplateResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@Controller
public class VoucherController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    VoucherService voucherService;

    @PostMapping("/voucher/create")
    public ResponseEntity<CreateVoucherResponse> createVoucher(@RequestBody CreateVoucherRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.CUSTOMER));
        CreateVoucherResponse response = voucherService.createNewVoucher(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/voucher/search")
    public ResponseEntity<SearchVoucherResponse> searchVoucherTemplate(@RequestBody SearchVoucherRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER, UserType.CUSTOMER));
        SearchVoucherResponse response = voucherService.searchVoucher(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
