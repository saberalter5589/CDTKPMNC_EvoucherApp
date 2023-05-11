package com.evoucherapp.evoucher.dto.response.voucher;

import com.evoucherapp.evoucher.dto.obj.VoucherDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchVoucherResponse {
    private List<VoucherDto> voucherDtoList;
}
