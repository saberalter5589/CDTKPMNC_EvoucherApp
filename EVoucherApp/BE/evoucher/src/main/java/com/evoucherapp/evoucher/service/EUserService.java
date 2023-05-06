package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.user.*;
import com.evoucherapp.evoucher.dto.response.user.CreateUserResponse;
import com.evoucherapp.evoucher.dto.response.user.GetCustomerListResponse;
import com.evoucherapp.evoucher.dto.response.user.GetPartnerListResponse;

public interface EUserService {
    CreateUserResponse createNewUser(CreateUserRequest request);
    GetCustomerListResponse searchCustomer(GetCustomerListRequest request);
    GetPartnerListResponse searchPartner(GetPartnerListRequest request);
    void updateUser(Long userId, UpdateUserRequest request);
    void deleteUser(Long userId, DeleteUserRequest request);
}
