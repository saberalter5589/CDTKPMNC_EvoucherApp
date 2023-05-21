package com.evoucherapp.evoucher.dto.request.partnertype;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class SearchPartnerTypeRequest extends BaseSearchingRequest {
    private Long partnerTypeId;
    private String partnerTypeName;
    private String partnerTypeCode;
}
