package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.BranchVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchVoucherRepository extends JpaRepository<BranchVoucher, Long> {
    @Query("SELECT bv FROM BranchVoucher bv WHERE bv.voucherTemplateId =:vTemplateId AND bv.isDeleted = false ")
    List<BranchVoucher> findBranchVoucherOfVoucherId(Long vTemplateId);
}
