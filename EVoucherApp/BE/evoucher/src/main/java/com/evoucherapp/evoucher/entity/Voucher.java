package com.evoucherapp.evoucher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "voucher")
@Entity
public class Voucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherId;
    private Long customerId;
    private Long voucherTemplateId;
    private String voucherCode;
    private String voucherName;
    private String description;
    private Boolean isValid;
    private Boolean isUsed;
}
