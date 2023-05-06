package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.obj.CustomerDto;
import com.evoucherapp.evoucher.dto.obj.PartnerDto;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartnerDxo {
    public static List<PartnerDto> mapFromDbObjListToPartnerDtoList(List<Object[]> dbObjList){
        List<PartnerDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObtoPartnerDto(obj));
            }
        }
        return resultList;
    }

    private static PartnerDto mapFromDbObtoPartnerDto(Object[] obj){
        if(obj == null){
            return null;
        }
        PartnerDto dto = new PartnerDto();
        dto.setUserId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setUserTypeId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        dto.setUserName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setEmail(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setPhone(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setAddress(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        dto.setPartnerTypeId(obj[6] != null ? ObjectUtil.getValueOfLong(obj[6]) : null);
        dto.setPartnerName(obj[7] != null ? ObjectUtil.getValueOfString(obj[7]) : null);
        dto.setPartnerNote(obj[8] != null ? ObjectUtil.getValueOfString(obj[8]) : null);
        dto.setIsDeleted(false);
        return dto;
    }
}
