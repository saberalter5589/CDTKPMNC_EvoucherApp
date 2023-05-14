package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.login.LoginRequest;
import com.evoucherapp.evoucher.dto.request.user.CreateCustomerRequest;
import com.evoucherapp.evoucher.dto.response.login.LoginResponse;
import com.evoucherapp.evoucher.dto.response.user.CreateUserResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@Controller
public class LoginController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> addCustomer(@RequestBody LoginRequest request){
        LoginResponse response = authenticationService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
