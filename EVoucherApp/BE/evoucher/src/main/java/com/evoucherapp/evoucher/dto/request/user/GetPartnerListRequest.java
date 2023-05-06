package com.evoucherapp.evoucher.dto.request.user;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class GetPartnerListRequest extends BaseSearchingRequest {
    private String id;
    private String username;
    private String email;
    private String address;
    private Long partnerTypeId;
    private String partnerName;
}
