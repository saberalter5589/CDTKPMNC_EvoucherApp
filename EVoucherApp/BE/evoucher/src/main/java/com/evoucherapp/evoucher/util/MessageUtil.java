package com.evoucherapp.evoucher.util;

import com.evoucherapp.evoucher.common.enums.MessageEnum;
import com.evoucherapp.evoucher.dto.MessageInfo;

import java.text.MessageFormat;

public class MessageUtil {
    public static MessageInfo formatMessage(Integer code, String... params){
        MessageEnum messageEnum = MessageEnum.getByCode(code);
        String customMessage = messageEnum.getMessage();
        if(params.length > 0){
            customMessage = MessageFormat.format(customMessage, (Object[]) params);
        }
        return new MessageInfo(messageEnum.getCode().toString(), customMessage);
    }
}
