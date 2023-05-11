package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.voucher.CreateVoucherRequest;
import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;
import com.evoucherapp.evoucher.dto.response.voucher.CreateVoucherResponse;
import com.evoucherapp.evoucher.dto.response.voucher.SearchVoucherResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.CreateVoucherTemplateResponse;

public interface VoucherService {
    CreateVoucherResponse createNewVoucher(CreateVoucherRequest request);
    SearchVoucherResponse searchVoucher(SearchVoucherRequest request);
}
