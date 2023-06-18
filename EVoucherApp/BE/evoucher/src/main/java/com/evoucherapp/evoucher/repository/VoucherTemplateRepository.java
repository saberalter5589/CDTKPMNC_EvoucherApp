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

    @Query(value = "\tSELECT *\n" +
            "\tFROM voucher_template vt1\n" +
            "\tWHERE vt1.campain_id =:campainId AND vt1.is_deleted = false\n" +
            "\tAND vt1.amount > (\n" +
            "\t\tSELECT COUNT(vc.customer_id) as v_count\n" +
            "\t\tFROM voucher_template vt\n" +
            "\t\tLEFT JOIN voucher vc ON vt.voucher_template_id = vc.voucher_template_id\n" +
            "\t\tWHERE vt.campain_id =:campainId AND vt.is_deleted = false AND vt1.voucher_template_id = vt.voucher_template_id\n" +
            "\tGROUP BY vt.voucher_template_id\n" +
            "\t)\n" +
            "\tORDER BY vt1.voucher_template_id", nativeQuery = true)
    List<VoucherTemplate> getVoucherTemplateRandom(Long campainId);
}
