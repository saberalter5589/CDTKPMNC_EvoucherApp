package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.user.CreateCustomerRequest;
import com.evoucherapp.evoucher.dto.response.CreateBranchResponse;
import com.evoucherapp.evoucher.dto.response.user.CreateUserResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@Controller
public class BranchController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/branch/create")
    public ResponseEntity<CreateBranchResponse> addBranch(@RequestBody CreateBranchRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        CreateBranchResponse response = new CreateBranchResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
