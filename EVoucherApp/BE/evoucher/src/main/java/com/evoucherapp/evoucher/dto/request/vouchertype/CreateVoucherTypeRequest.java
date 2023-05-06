package com.evoucherapp.evoucher.dto.request.vouchertype;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

@Data
public class CreateVoucherTypeRequest extends BaseRequest {
    private String voucherTypeCode;
    private String voucherTypeName;
}
