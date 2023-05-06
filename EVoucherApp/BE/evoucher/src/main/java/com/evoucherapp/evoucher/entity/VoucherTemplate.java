package com.evoucherapp.evoucher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "voucher_template")
@Entity
public class VoucherTemplate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherTemplateId;
    private Long voucherTypeId;
    private Long campainId;
    private String voucherTemplateCode;
    private String voucherTemplateName;
    private Long amount;
    private String description;
    private String note;
    private LocalDate dateStart;
    private LocalDate dateEnd;
}
