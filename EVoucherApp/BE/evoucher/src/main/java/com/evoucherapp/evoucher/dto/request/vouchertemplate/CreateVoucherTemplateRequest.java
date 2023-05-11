package com.evoucherapp.evoucher.dto.request.vouchertemplate;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateVoucherTemplateRequest extends BaseRequest {
    Long voucherTypeId;
    Long campaignId;
    String voucherTemplateCode;
    String voucherTemplateName;
    Long amount;
    String description;
    String note;
    String dateStart;
    String dateEnd;
    List<Long> branchIds = new ArrayList<>();
}
