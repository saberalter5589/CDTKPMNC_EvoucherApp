package com.evoucherapp.evoucher.dto.request.vouchertype;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class GetVoucherTypeListRequest extends BaseSearchingRequest {
    private String code;
    private String name;
}
