package com.evoucherapp.evoucher.dto.response.login;

import com.evoucherapp.evoucher.dto.obj.UserDto;
import lombok.Data;

@Data
public class LoginResponse {
    private UserDto user;
}
