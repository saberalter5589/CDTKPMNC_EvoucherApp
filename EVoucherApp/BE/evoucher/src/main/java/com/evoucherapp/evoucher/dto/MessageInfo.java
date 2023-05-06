package com.evoucherapp.evoucher.dto;

import com.evoucherapp.evoucher.common.enums.MessageEnum;
import lombok.Data;

@Data
public class MessageInfo {
    String code;
    String message;

    public MessageInfo(String code, String message){
        this.code = code;
        this.message = message;
    }
}
