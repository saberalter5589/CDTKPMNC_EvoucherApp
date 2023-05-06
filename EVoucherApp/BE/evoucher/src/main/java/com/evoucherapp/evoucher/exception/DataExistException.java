package com.evoucherapp.evoucher.exception;

import com.evoucherapp.evoucher.dto.MessageInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DataExistException extends BaseException{
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public DataExistException(MessageInfo messageInfo){
        super(messageInfo.getMessage());
        this.messageInfo = messageInfo;
    }
}
