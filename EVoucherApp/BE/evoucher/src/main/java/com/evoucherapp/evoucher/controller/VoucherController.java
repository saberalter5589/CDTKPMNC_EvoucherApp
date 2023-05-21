package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.voucher.CreateVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.UseVoucherRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.CreateVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
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

    @PostMapping("/voucher/use-voucher/{id}")
    public ResponseEntity<SuccessResponse> useVoucher(@PathVariable("id") Long id, @RequestBody UseVoucherRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.CUSTOMER));
        voucherService.useVoucher(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @GetMapping("/voucher/search")
    public ResponseEntity<SearchVoucherResponse> searchVoucherTemplate(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @RequestParam(value = "voucherId", required = false) Long voucherId,
            @RequestParam(value = "voucherCode", required = false) String voucherCode,
            @RequestParam(value = "voucherName", required = false) String voucherName,
            @RequestParam(value = "partnerId", required = false) Long partnerId,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed){
        SearchVoucherRequest request = new SearchVoucherRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);

        request.setVoucherId(voucherId);
        request.setVoucherCode(voucherCode);
        request.setVoucherName(voucherName);
        request.setIsUsed(isUsed);
        request.setPartnerId(partnerId);

        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER, UserType.CUSTOMER));
        SearchVoucherResponse response = voucherService.searchVoucher(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
