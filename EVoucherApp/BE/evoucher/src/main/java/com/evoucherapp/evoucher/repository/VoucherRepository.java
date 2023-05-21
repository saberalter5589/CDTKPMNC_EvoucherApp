package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.PartnerType;
import com.evoucherapp.evoucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, VoucherRepositoryCustom {
    @Query("SELECT vc FROM Voucher vc WHERE vc.voucherId =:voucherId AND vc.customerId =:customerId ")
    Voucher findByVoucherIdAndCustomerId(Long voucherId, Long customerId);
}
