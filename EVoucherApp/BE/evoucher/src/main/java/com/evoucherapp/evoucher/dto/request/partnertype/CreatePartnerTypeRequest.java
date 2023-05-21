package com.evoucherapp.evoucher.dto.request.partnertype;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

@Data
public class CreatePartnerTypeRequest extends BaseRequest {
    private String partnerTypeCode;
    private String partnerTypeName;
}
