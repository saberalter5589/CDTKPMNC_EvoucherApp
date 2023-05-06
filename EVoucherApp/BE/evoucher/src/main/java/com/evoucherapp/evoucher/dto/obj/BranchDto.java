package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

@Data
public class BranchDto {
    private Long branchId;
    private Long partnerId;
    private String partnerName;
    private String branchName;
    private String address;
    private String phone;
}
