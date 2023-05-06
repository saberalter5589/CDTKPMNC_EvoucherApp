package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

@Data
public class BranchDto {
    private Long branchId;
    private String branchName;
    private String address;
    private String phone;
    private String partnerName;
    private Long partnerId;
}
