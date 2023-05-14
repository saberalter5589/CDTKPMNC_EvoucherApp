package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.login.LoginRequest;
import com.evoucherapp.evoucher.dto.response.login.LoginResponse;

import java.util.List;

public interface AuthenticationService {
    void validateUser(BaseRequest request, List<Long> userTypeList);
    LoginResponse login(LoginRequest request);
}
