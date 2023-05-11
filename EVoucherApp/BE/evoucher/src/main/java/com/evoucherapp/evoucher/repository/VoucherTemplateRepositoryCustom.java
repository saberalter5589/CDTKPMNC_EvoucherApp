package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;

import java.util.List;

public interface VoucherTemplateRepositoryCustom {
    List<Object[]> searchVoucherTemplate(SearchVoucherTemplateRequest request);
}
