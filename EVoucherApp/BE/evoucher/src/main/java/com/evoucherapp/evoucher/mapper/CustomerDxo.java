package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.obj.CustomerDto;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDxo {
    public static List<CustomerDto> mapFromDbObjListToCustomerDtoList(List<Object[]> dbObjList){
        List<CustomerDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObtoCustomerDto(obj));
            }
        }
        return resultList;
    }

    private static CustomerDto mapFromDbObtoCustomerDto(Object[] obj){
        if(obj == null){
            return null;
        }
        CustomerDto dto = new CustomerDto();
        dto.setUserId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setUserTypeId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        dto.setUserName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setEmail(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setPhone(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setAddress(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        dto.setCustomerName(obj[6] != null ? ObjectUtil.getValueOfString(obj[6]) : null);
        dto.setIsDeleted(false);
        dto.setBirthday(obj[7] != null ? DateTimeUtil.convertFromDateToString((Date)obj[7], DateTimeFormat.DATE): null);
        return dto;
    }
}
