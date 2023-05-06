package com.evoucherapp.evoucher.dto.response.user;

import com.evoucherapp.evoucher.dto.obj.CustomerDto;
import lombok.Data;

import java.util.List;

@Data
public class GetCustomerListResponse {
    private List<CustomerDto> customerList;
}
