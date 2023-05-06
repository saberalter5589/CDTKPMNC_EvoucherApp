package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.branch.CreateBranchRequest;
import com.evoucherapp.evoucher.dto.response.CreateBranchResponse;

public interface BranchService {
    CreateBranchResponse createNewBranch(CreateBranchRequest request);
}
