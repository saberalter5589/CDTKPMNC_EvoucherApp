package com.evoucherapp.evoucher.dto.obj.statistic;

import lombok.Data;

@Data
public class CampaignStatisticDto {
    private Long campaignId;
    private String campaignCode;
    private String campaignName;
    private Long totalCustomer;
    private Long totalVoucher;
    private Long totalUsedVoucher;
}
