package com.evoucherapp.evoucher.exception;

import com.evoucherapp.evoucher.dto.MessageInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UnAuthorizationException extends BaseException {
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public UnAuthorizationException(MessageInfo messageInfo){
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }
}
