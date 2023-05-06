package com.evoucherapp.evoucher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "campain")
@Entity
public class Campaign extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campainId;
    private Long partnerId;
    private String campainCode;
    private String campainName;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;
    private String note;
    private Long status;
}
