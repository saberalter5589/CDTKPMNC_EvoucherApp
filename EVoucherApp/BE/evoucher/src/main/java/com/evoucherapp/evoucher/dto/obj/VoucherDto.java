package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoucherDto {
    private Long voucherId;
    private PartnerDto partner;
    private CustomerDto customer;
    private VoucherTemplateDto voucherTemplate;
    private String voucherCode;
    private String voucherName;
    private String description;
    private Boolean isValid;
    private Boolean isUsed;
    private String dateStart;
    private String dateEnd;
    private List<BranchDto> branchList = new ArrayList<>();
}
