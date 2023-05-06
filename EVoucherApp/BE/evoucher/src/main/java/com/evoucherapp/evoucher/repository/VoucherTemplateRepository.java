package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.VoucherTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherTemplateRepository extends JpaRepository<VoucherTemplate, Long> {
}
