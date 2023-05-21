package com.evoucherapp.evoucher.dto.response.partnertype;

import com.evoucherapp.evoucher.dto.obj.PartnerTypeDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchPartnerTypeResponse {
    List<PartnerTypeDto> partnerTypeDtoList;
}
