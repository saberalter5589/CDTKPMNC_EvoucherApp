package com.evoucherapp.evoucher.dto.request.branch;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

@Data
public class UpdateBranchRequest extends BaseRequest {
    String branchName;
    String address;
    String phone;
}
