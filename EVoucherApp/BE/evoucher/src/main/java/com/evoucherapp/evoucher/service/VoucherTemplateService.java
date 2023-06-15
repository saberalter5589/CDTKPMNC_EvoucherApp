package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.CreateVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.CreateVoucherTemplateResponse;
import com.evoucherapp.evoucher.dto.response.vouchertemplate.SearchVoucherTemplateResponse;

public interface VoucherTemplateService {
    CreateVoucherTemplateResponse createVoucherTemplate(CreateVoucherTemplateRequest request);
    SearchVoucherTemplateResponse searchVoucherTemplate(SearchVoucherTemplateRequest request);
    void updateVoucherTemplate(Long vTemplateId, CreateVoucherTemplateRequest request);
    void deleteVoucherTemplate(Long vTemplateId, BaseRequest request);
}
