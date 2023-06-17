package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignStatisticRequest;
import com.evoucherapp.evoucher.dto.request.user.GetCustomerListRequest;

import java.util.List;

public interface CampaignRepositoryCustom {
    List<Object[]> searchCampaign(SearchCampaignRequest request);
    List<Object[]> searchCampaignStatistic(SearchCampaignStatisticRequest request);
}
