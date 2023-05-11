package com.evoucherapp.evoucher.dto.request.vouchertemplate;

import com.evoucherapp.evoucher.dto.obj.BranchDto;
import com.evoucherapp.evoucher.dto.obj.VoucherDto;
import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

import java.util.List;

@Data
public class SearchVoucherTemplateRequest extends BaseSearchingRequest {
    Long voucherTemplateId;
    Long voucherTypeId;
    Long campaignId;
    String voucherTemplateCode;
    String voucherTemplateName;
    String dateStart;
    String dateEnd;
}
