package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoucherTemplateDto {
    Long voucherTemplateId;
    VoucherTypeDto voucherType;
    CampaignDto campaign;
    String voucherTemplateCode;
    String voucherTemplateName;
    Long amount;
    String description;
    String note;
    String dateStart;
    String dateEnd;
    List<BranchDto> branchList = new ArrayList<>();
    List<VoucherDto> voucherList = new ArrayList<>();
}
