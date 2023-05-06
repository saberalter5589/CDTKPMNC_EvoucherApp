package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.request.branch.UpdateBranchRequest;
import com.evoucherapp.evoucher.dto.response.branch.CreateBranchResponse;
import com.evoucherapp.evoucher.dto.response.branch.GetBranchListResponse;

public interface BranchService {
    CreateBranchResponse createNewBranch(CreateBranchRequest request);
    GetBranchListResponse searchBranch(GetBranchListRequest request);
    void updateBranch(Long branchId, UpdateBranchRequest request);
    void deleteBranch(Long branchId, BaseRequest request);
}
