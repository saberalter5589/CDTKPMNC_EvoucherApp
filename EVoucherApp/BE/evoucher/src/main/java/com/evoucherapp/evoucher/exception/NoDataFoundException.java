package com.evoucherapp.evoucher.exception;

import com.evoucherapp.evoucher.dto.MessageInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NoDataFoundException extends BaseException {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NoDataFoundException(MessageInfo messageInfo){
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }
}
