package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.BaseRequest;

import java.util.List;

public interface AuthenticationService {
    void validateUser(BaseRequest request, List<Long> userTypeList);
}
