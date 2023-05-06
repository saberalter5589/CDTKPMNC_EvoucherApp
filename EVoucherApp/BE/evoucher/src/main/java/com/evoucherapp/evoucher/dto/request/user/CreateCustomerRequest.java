package com.evoucherapp.evoucher.dto.request.user;

import lombok.Data;

@Data
public class CreateCustomerRequest extends CreateUserRequest{
    String customerName;
    String birthday;
}
