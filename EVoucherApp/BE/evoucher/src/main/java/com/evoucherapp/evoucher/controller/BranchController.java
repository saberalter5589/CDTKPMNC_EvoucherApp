package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.request.branch.UpdateBranchRequest;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import com.evoucherapp.evoucher.dto.response.branch.CreateBranchResponse;
import com.evoucherapp.evoucher.dto.response.branch.GetBranchListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class BranchController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    BranchService branchService;

    @PostMapping("/branch/create")
    public ResponseEntity<CreateBranchResponse> addBranch(@RequestBody CreateBranchRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        CreateBranchResponse response = branchService.createNewBranch(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/branch/search")
    public ResponseEntity<GetBranchListResponse> searchCustomer(@RequestBody GetBranchListRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER));
        GetBranchListResponse response = branchService.searchBranch(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/branch/{id}")
    public ResponseEntity<SuccessResponse> editBranch(@PathVariable("id") Long id, @RequestBody UpdateBranchRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        branchService.updateBranch(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/branch/{id}")
    public ResponseEntity<SuccessResponse> deleteCustomer(@PathVariable("id") Long id, @RequestBody BaseRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        branchService.deleteBranch(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
