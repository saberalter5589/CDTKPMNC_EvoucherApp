package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;

import java.util.List;

public interface BranchRepositoryCustom {
    List<Object[]> searchBranch(GetBranchListRequest request);
}
