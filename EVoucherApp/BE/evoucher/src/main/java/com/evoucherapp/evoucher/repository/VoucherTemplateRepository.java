package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.VoucherTemplate;
import com.evoucherapp.evoucher.entity.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherTemplateRepository extends JpaRepository<VoucherTemplate, Long>, VoucherTemplateRepositoryCustom {
    @Query("SELECT vt FROM VoucherTemplate vt WHERE vt.voucherTemplateCode =:code AND vt.isDeleted = false ")
    VoucherTemplate findByCode(String code);

    @Query("SELECT vt FROM VoucherTemplate vt WHERE vt.voucherTemplateId =:id AND vt.isDeleted = false ")
    VoucherTemplate findByVoucherTemplateId(Long id);

    @Query("SELECT vt FROM VoucherTemplate vt WHERE vt.campainId =:campainId AND vt.isDeleted = false ")
    List<VoucherTemplate> findByCampainId(Long campainId);
}
