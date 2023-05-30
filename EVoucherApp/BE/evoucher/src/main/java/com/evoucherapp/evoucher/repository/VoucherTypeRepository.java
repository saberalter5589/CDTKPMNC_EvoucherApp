package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Partner;
import com.evoucherapp.evoucher.entity.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherTypeRepository extends JpaRepository<VoucherType, Long> {
    @Query("SELECT vct FROM VoucherType vct WHERE vct.voucherTypeCode =:voucherTypeCode AND vct.isDeleted = false ")
    VoucherType findByCode(String voucherTypeCode);
    @Query("SELECT vct FROM VoucherType vct WHERE vct.voucherTypeId =:id AND vct.isDeleted = false ")
    VoucherType findByVoucherTypeId(Long id);
    @Query("SELECT vct FROM VoucherType vct WHERE vct.isDeleted = false ")
    List<VoucherType> findAll();
}
