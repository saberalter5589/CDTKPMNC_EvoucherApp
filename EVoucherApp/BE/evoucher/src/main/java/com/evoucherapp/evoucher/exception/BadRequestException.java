package com.evoucherapp.evoucher.exception;

import com.evoucherapp.evoucher.dto.MessageInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BadRequestException extends BaseException {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadRequestException(MessageInfo messageInfo){
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }
}
