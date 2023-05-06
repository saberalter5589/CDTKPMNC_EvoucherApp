package com.evoucherapp.evoucher.dto.request.user;

import com.evoucherapp.evoucher.dto.obj.UserDto;
import lombok.Data;

@Data
public class CreateUserRequest {
    Long userTypeId;
    String userName;
    String password;
    String email;
    String phone;
    String address;
}
