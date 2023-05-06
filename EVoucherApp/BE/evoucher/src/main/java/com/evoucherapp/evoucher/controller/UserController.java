package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.user.*;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import com.evoucherapp.evoucher.dto.response.user.CreateUserResponse;
import com.evoucherapp.evoucher.dto.response.user.GetCustomerListResponse;
import com.evoucherapp.evoucher.dto.response.user.GetPartnerListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.EUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class UserController {
    @Autowired
    EUserService eUserService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/customer/search")
    public ResponseEntity<GetCustomerListResponse> searchCustomer(@RequestBody GetCustomerListRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER));
        GetCustomerListResponse response = eUserService.searchCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/partner/search")
    public ResponseEntity<GetPartnerListResponse> searchPartner(@RequestBody GetPartnerListRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER));
        GetPartnerListResponse response = eUserService.searchPartner(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/customer/create")
    public ResponseEntity<CreateUserResponse> addCustomer(@RequestBody CreateCustomerRequest request){
        request.setUserTypeId(UserType.CUSTOMER);
        CreateUserResponse response = eUserService.createNewUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/partner/create")
    public ResponseEntity<CreateUserResponse> addPartner(@RequestBody CreatePartnerRequest request){
        request.setUserTypeId(UserType.PARTNER);
        CreateUserResponse response = eUserService.createNewUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<SuccessResponse> editCustomer(@PathVariable("id") Long id, @RequestBody UpdateCustomerRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.CUSTOMER));
        request.setUserTypeId(UserType.CUSTOMER);
        eUserService.updateUser(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @PutMapping("/partner/{id}")
    public ResponseEntity<SuccessResponse> editPartner(@PathVariable("id") Long id, @RequestBody UpdatePartnerRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        request.setUserTypeId(UserType.PARTNER);
        eUserService.updateUser(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<SuccessResponse> deleteCustomer(@PathVariable("id") Long id, @RequestBody DeleteUserRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.CUSTOMER));
        request.setUserTypeId(UserType.CUSTOMER);
        eUserService.deleteUser(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/partner/{id}")
    public ResponseEntity<SuccessResponse> deletePartner(@PathVariable("id") Long id, @RequestBody DeleteUserRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.CUSTOMER));
        request.setUserTypeId(UserType.PARTNER);
        eUserService.deleteUser(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
