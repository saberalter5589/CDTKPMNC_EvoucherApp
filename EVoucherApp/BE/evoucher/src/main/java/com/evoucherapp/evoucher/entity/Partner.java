package com.evoucherapp.evoucher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "partner")
@Entity
public class Partner extends BaseEntity{
    @Id
    private Long partnerId;
    private Long partnerTypeId;
    private String partnerName;
    private String note;
}
