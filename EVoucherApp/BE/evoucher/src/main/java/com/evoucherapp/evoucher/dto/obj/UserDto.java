package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

@Data
public class UserDto {
    Long userTypeId;
    Long userId;
    String userName;
    String email;
    String phone;
    String address;
    Boolean isDeleted;
    String password;
}
