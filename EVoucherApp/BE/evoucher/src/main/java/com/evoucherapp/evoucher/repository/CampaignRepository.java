package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Branch;
import com.evoucherapp.evoucher.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CampaignRepository extends JpaRepository<Campaign, Long>, CampaignRepositoryCustom {
    @Query("SELECT cp FROM Campaign cp WHERE cp.campainCode =:code AND cp.isDeleted = false ")
    Campaign findByCode(String code);

    @Query("SELECT cp FROM Campaign cp WHERE cp.campainId =:id AND cp.isDeleted = false ")
    Campaign findByCampaignId(Long id);
}
