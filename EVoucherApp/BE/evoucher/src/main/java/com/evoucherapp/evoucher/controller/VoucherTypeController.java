package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.CreateVoucherTypeRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.GetVoucherTypeListRequest;
import com.evoucherapp.evoucher.dto.response.branch.CreateBranchResponse;
import com.evoucherapp.evoucher.dto.response.branch.GetBranchListResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.CreateVoucherTypeResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.GetVoucherTypeListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.VoucherTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@Controller
public class VoucherTypeController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    VoucherTypeService voucherTypeService;

    @PostMapping("/voucher-type/create")
    public ResponseEntity<CreateVoucherTypeResponse> addBranch(@RequestBody CreateVoucherTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN));
        CreateVoucherTypeResponse response = voucherTypeService.createVoucherType(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/voucher-type/search")
    public ResponseEntity<GetVoucherTypeListResponse> searchCustomer(@RequestBody GetVoucherTypeListRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER));
        GetVoucherTypeListResponse response = voucherTypeService.searchVoucherTypeList(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
