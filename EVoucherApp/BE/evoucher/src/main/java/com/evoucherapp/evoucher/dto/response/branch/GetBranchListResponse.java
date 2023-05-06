package com.evoucherapp.evoucher.dto.response.branch;

import com.evoucherapp.evoucher.dto.obj.BranchDto;
import lombok.Data;

import java.util.List;

@Data
public class GetBranchListResponse {
    private List<BranchDto> branchList;
}
