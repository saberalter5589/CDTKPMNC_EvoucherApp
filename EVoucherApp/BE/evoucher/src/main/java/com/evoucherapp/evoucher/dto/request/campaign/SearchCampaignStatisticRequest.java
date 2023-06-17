package com.evoucherapp.evoucher.dto.request.campaign;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class SearchCampaignStatisticRequest extends BaseSearchingRequest {
    Long partnerId;
    Long campainId;
}
