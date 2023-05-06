package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
