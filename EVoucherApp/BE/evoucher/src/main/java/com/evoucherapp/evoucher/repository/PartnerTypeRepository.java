package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Partner;
import com.evoucherapp.evoucher.entity.PartnerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartnerTypeRepository extends JpaRepository<PartnerType, Long> {
    @Query("SELECT pt FROM PartnerType pt WHERE pt.partnerTypeId =:id AND pt.isDeleted = false")
    PartnerType findByPartnerTypeId(@Param("id") Long id);

    @Query("SELECT pt FROM PartnerType pt WHERE pt.isDeleted = false")
    List<PartnerType> findAll();
}
