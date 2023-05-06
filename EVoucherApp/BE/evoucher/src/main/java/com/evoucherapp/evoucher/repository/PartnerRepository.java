package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Customer;
import com.evoucherapp.evoucher.entity.EUser;
import com.evoucherapp.evoucher.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    @Query("SELECT pn FROM Partner pn WHERE pn.partnerId =:partnerId AND pn.isDeleted = false ")
    Partner findByPartnerId(Long partnerId);
}
