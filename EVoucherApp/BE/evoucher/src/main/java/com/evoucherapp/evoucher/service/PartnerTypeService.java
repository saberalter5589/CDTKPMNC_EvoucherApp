package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.partnertype.CreatePartnerTypeRequest;
import com.evoucherapp.evoucher.dto.request.partnertype.SearchPartnerTypeRequest;
import com.evoucherapp.evoucher.dto.response.partnertype.CreatePartnerTypeResponse;
import com.evoucherapp.evoucher.dto.response.partnertype.SearchPartnerTypeResponse;

public interface PartnerTypeService {
    CreatePartnerTypeResponse createPartnerType(CreatePartnerTypeRequest request);
    SearchPartnerTypeResponse searchPartnerType(SearchPartnerTypeRequest request);
    void updatePartnerType(Long id, CreatePartnerTypeRequest request);
}
