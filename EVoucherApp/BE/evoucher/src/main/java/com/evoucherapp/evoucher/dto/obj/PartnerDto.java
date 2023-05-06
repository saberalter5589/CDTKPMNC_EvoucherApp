package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

@Data
public class PartnerDto extends UserDto {
    Long partnerTypeId;
    String partnerName;
    String partnerNote;
}
