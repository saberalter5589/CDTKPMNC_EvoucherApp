package com.evoucherapp.evoucher.dto.response;

import com.evoucherapp.evoucher.dto.obj.BranchDto;
import lombok.Data;

@Data
public class CreateBranchResponse extends SuccessResponse {
    private BranchDto branchDto;
}
