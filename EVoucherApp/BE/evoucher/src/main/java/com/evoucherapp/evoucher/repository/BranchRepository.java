package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.Customer;
import com.evoucherapp.evoucher.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Long>, BranchRepositoryCustom {
    @Query("SELECT br FROM Branch br WHERE br.branchId =:branchId AND br.isDeleted = false ")
    Branch findByBranchId(Long branchId);
}
