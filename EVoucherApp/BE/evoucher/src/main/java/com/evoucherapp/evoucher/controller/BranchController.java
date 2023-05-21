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
@CrossOrigin(origins = "*")
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
    public ResponseEntity<GetBranchListResponse> searchBranch(@RequestHeader("userId") Long userId,
                                                                @RequestHeader("password") String password,
                                                                @RequestParam(value = "branchId", required = false) Long branchId,
                                                                @RequestParam(value="branchName", required = false) String branchName,
                                                                @RequestParam(value="phone", required = false) String phone,
                                                                @RequestParam(value="address", required = false) String address,
                                                                @RequestParam(value="partnerId", required = false) Long partnerId){
        GetBranchListRequest request = new GetBranchListRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setBranchId(branchId);
        request.setBranchName(branchName);
        request.setPhone(phone);
        request.setAddress(address);
        request.setPartnerId(partnerId);
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
    public ResponseEntity<SuccessResponse> deleteCustomer(@RequestHeader("userId") Long userId,
                                                          @RequestHeader("password") String password,
                                                          @PathVariable("id") Long id){
        BaseRequest request = new BaseRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserType.PARTNER));
        branchService.deleteBranch(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
