package com.evoucherapp.evoucher.dto.response.campaign;

import com.evoucherapp.evoucher.dto.obj.BranchDto;
import com.evoucherapp.evoucher.dto.obj.CampaignDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchCampaignResponse {
    private List<CampaignDto> campaignDtoList;
}
