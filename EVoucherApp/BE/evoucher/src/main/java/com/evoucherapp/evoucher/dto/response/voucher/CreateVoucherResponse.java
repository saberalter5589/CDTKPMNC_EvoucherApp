package com.evoucherapp.evoucher.dto.response.voucher;

import com.evoucherapp.evoucher.dto.obj.VoucherDto;
import lombok.Data;

@Data
public class CreateVoucherResponse {
    private VoucherDto voucher;
}
