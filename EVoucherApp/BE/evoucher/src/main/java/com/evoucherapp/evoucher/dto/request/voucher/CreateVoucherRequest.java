package com.evoucherapp.evoucher.dto.request.voucher;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

@Data
public class CreateVoucherRequest extends BaseRequest {
    private Long campaignId;
}
