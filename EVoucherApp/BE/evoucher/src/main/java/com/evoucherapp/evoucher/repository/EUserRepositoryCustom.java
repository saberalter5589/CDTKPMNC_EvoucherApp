package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.dto.request.user.GetCustomerListRequest;
import com.evoucherapp.evoucher.dto.request.user.GetPartnerListRequest;

import java.util.List;

public interface EUserRepositoryCustom {
    List<Object[]> searchCustomer(GetCustomerListRequest request);
    List<Object[]> searchPartner(GetPartnerListRequest request);
}
