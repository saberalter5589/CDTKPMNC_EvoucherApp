package com.evoucherapp.evoucher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer")
@Entity
public class Customer extends BaseEntity{
    @Id
    private Long customerId;
    private String customerName;
    private LocalDate birthday;
}
