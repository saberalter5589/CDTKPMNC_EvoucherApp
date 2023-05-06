package com.evoucherapp.evoucher.common.enums;

import lombok.Data;

import java.util.Arrays;
import java.util.Optional;


public enum MessageEnum {
    M_10001(10001, "Cannot find {0}, please check again"),
    M_10002(10002, "{0} is exist, please input another one"),
    M_10003(10003, "You are not authorized or authenticated to use")
;
    private Integer code;
    private String message;

    MessageEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }

    public static MessageEnum getByCode(Integer code) {
        Optional<MessageEnum> msgOp = Arrays.stream(MessageEnum.values())
                .filter(msg -> msg.code.equals(code))
                .findFirst();
        return msgOp.orElse(null);
    }
}
