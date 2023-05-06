package com.evoucherapp.evoucher.dto.request.campaign;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class SearchCampaignRequest extends BaseSearchingRequest {
    private Long campainId;
    private Long partnerId;
    private String campaignCode;
    private String campainName;
    private String dateStart;
    private String dateEnd;
    private Long status;
}
