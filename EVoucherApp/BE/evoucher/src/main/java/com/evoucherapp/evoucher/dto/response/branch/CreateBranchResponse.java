package com.evoucherapp.evoucher.dto.response.branch;

import com.evoucherapp.evoucher.dto.obj.BranchDto;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import lombok.Data;

@Data
public class CreateBranchResponse extends SuccessResponse {
    private BranchDto branchDto;
}
