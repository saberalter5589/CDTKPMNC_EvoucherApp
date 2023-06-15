package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.campaign.CreateCampaignRequest;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.response.campaign.CreateCampaignResponse;
import com.evoucherapp.evoucher.dto.response.campaign.SearchCampaignResponse;

public interface CampaignService {
    CreateCampaignResponse createCampaign(CreateCampaignRequest request);
    void updateCampaign(Long campaignId, CreateCampaignRequest request);
    SearchCampaignResponse searchCampaign(SearchCampaignRequest request);
    void deleteCampaign(Long campaignId, BaseRequest request);
}
