package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Campaign;
import com.evoucherapp.evoucher.entity.CampaignGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignGameRepository extends JpaRepository<CampaignGame, Long> {
    @Query("SELECT cpg FROM CampaignGame cpg WHERE cpg.campainId =:campainId AND cpg.isDeleted = false ")
    List<CampaignGame> findByCampainId(Long campainId);

}
