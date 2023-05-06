package com.evoucherapp.evoucher.dto.request.branch;

import com.evoucherapp.evoucher.dto.request.BaseSearchingRequest;
import lombok.Data;

@Data
public class GetBranchListRequest extends BaseSearchingRequest {
    private Long branchId;
    private String branchName;
    private String phone;
    private String address;
    private Long partnerId;
}
