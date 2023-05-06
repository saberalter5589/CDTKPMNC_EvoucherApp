package com.evoucherapp.evoucher.dto.response.user;

import com.evoucherapp.evoucher.dto.obj.PartnerDto;
import lombok.Data;

import java.util.List;

@Data
public class GetPartnerListResponse {
    List<PartnerDto> partnerList;
}
