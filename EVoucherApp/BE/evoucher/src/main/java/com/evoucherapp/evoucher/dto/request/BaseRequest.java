package com.evoucherapp.evoucher.dto.request;

import com.evoucherapp.evoucher.dto.obj.AuthDto;
import lombok.Data;

@Data
public class BaseRequest {
     private AuthDto authentication;
}
