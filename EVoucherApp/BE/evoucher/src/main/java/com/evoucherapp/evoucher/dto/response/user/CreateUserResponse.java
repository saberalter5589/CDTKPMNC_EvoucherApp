package com.evoucherapp.evoucher.dto.response.user;

import com.evoucherapp.evoucher.dto.obj.UserDto;
import com.evoucherapp.evoucher.dto.response.SuccessResponse;
import lombok.Data;

@Data
public class CreateUserResponse extends SuccessResponse {
    UserDto user;
}
