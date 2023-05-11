package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.PartnerType;
import com.evoucherapp.evoucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, VoucherRepositoryCustom {
}
