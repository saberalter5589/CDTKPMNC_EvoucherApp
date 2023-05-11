package com.evoucherapp.evoucher.repository;

import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;

import java.util.List;

public interface VoucherRepositoryCustom {
    List<Object[]> searchVoucher(Long userId, Long userType, SearchVoucherRequest request);
}
