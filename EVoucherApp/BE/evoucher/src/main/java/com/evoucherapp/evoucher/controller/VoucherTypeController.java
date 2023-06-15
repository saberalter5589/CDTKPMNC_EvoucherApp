package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.request.partnertype.CreatePartnerTypeRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.CreateVoucherTypeRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.GetVoucherTypeListRequest;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class VoucherTypeController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    VoucherTypeService voucherTypeService;

    @PostMapping("/voucher-type/create")
    public ResponseEntity<CreateVoucherTypeResponse> addVoucherType(@RequestBody CreateVoucherTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN));
        CreateVoucherTypeResponse response = voucherTypeService.createVoucherType(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/voucher-type/search")
    public ResponseEntity<GetVoucherTypeListResponse> searchVoucherType( @RequestHeader("userId") Long userId,
                                                                         @RequestHeader("password") String password,
                                                                         @RequestParam(value = "voucherTemplateCode", required = false) String code,
                                                                         @RequestParam(value = "voucherTemplateName", required = false) String name){
        GetVoucherTypeListRequest request = new GetVoucherTypeListRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        request.setCode(code);
        request.setName(name);
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER));
        GetVoucherTypeListResponse response = voucherTypeService.searchVoucherTypeList(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/voucher-type/{id}")
    public ResponseEntity<SuccessResponse> updatePartnerType(@PathVariable("id") Long id, @RequestBody CreateVoucherTypeRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN));
        voucherTypeService.updateVoucherType(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/voucher-type/{id}")
    public ResponseEntity<SuccessResponse> deletePartnerType(
            @RequestHeader("userId") Long userId,
            @RequestHeader("password") String password,
            @PathVariable("id") Long id){
        BaseRequest request = new BaseRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN));
        voucherTypeService.deleteVoucherType(id, request);
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
}
