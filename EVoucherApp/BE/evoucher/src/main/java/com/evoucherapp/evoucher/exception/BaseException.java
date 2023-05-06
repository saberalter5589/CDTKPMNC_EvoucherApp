package com.evoucherapp.evoucher.exception;

import com.evoucherapp.evoucher.dto.MessageInfo;
import lombok.Data;

@Data
public class BaseException extends RuntimeException{
    MessageInfo messageInfo;

    public BaseException(){

    }

    public BaseException(String message){
        super(message);
    }
}
