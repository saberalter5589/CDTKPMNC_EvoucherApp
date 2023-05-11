package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.BranchVoucher;
import com.evoucherapp.evoucher.entity.CampaignCustomer;
import com.evoucherapp.evoucher.entity.VoucherTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignCustomerRepository extends JpaRepository<CampaignCustomer, Long> {
    @Query("SELECT cc FROM CampaignCustomer cc WHERE cc.campainId =:campainId AND cc.customerId =:customerId AND cc.isDeleted = false ")
    CampaignCustomer findByCampainIdAndCustomerId(Long campainId, Long customerId);
}
