package com.evoucherapp.evoucher.dto.request.user;

import lombok.Data;

@Data
public class UpdatePartnerRequest extends UpdateUserRequest{
    Long partnerTypeId;
    String partnerName;
    String note;
}
