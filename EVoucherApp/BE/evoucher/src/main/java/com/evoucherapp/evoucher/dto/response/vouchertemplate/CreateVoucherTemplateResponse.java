package com.evoucherapp.evoucher.dto.response.vouchertemplate;

import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import lombok.Data;

@Data
public class CreateVoucherTemplateResponse extends SuccessResponse {
    private Long voucherTemplateId;
}
