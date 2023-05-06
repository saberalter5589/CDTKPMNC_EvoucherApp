package com.evoucherapp.evoucher.dto.request.branch;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

@Data
public class CreateBranchRequest extends BaseRequest {
    private Long partnerId;
    private String branchName;
    private String address;
    private String phone;
}
