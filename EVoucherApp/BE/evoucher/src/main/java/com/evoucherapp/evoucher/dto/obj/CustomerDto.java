package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

@Data
public class CustomerDto extends UserDto {
    private String customerName;
    private String birthday;
}
