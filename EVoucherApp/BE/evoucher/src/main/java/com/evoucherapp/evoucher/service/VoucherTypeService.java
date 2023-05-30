package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.vouchertype.CreateVoucherTypeRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.GetVoucherTypeListRequest;
import com.evoucherapp.evoucher.dto.response.vouchertype.CreateVoucherTypeResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.GetVoucherTypeListResponse;

public interface VoucherTypeService {
    CreateVoucherTypeResponse createVoucherType(CreateVoucherTypeRequest request);
    GetVoucherTypeListResponse searchVoucherTypeList(GetVoucherTypeListRequest request);
    void updateVoucherType(Long id, CreateVoucherTypeRequest request);
}
