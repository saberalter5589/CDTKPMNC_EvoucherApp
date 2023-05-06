package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.entity.Customer;
import com.evoucherapp.evoucher.entity.EUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT cr FROM Customer cr WHERE cr.customerId =:customerId AND cr.isDeleted = false ")
    Customer findByUserId(Long customerId);
}
