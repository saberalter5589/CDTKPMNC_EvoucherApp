package com.evoucherapp.evoucher.dto.response.campaign;

import com.evoucherapp.evoucher.dto.obj.statistic.CampaignStatisticDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchCampaignStatisticResponse {
    private List<CampaignStatisticDto> campaignStatisticDtoList;
}
