package com.evoucherapp.evoucher.dto.request.voucher;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class SearchVoucherRequest extends BaseSearchingRequest {
    Long voucherId;
    String voucherCode;
    String voucherName;
    Long partnerId;
    Long customerId;
    String customerName;
    Long vTemplateId;
    String vTemplateCode;
    String vTemplateName;
    Boolean isValid;
    Boolean isUsed;
}
