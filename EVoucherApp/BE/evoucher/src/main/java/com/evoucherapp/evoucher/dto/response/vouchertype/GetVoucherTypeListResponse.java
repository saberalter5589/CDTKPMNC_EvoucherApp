package com.evoucherapp.evoucher.dto.response.vouchertype;

import com.evoucherapp.evoucher.dto.obj.VoucherTypeDto;
import lombok.Data;

import java.util.List;

@Data
public class GetVoucherTypeListResponse {
    private List<VoucherTypeDto> voucherTypeDtoList;
}
