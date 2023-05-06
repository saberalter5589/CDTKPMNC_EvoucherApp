package com.evoucherapp.evoucher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "voucher_type")
@Entity
public class VoucherType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherTypeId;
    private String voucherTypeCode;
    private String voucherTypeName;
}
