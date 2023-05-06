package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.dto.obj.BranchDto;
import com.evoucherapp.evoucher.dto.obj.CustomerDto;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

public class BranchDxo {
    public static List<BranchDto> mapFromDbObjListToBranchDtoList(List<Object[]> dbObjList){
        List<BranchDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToBranchDto(obj));
            }
        }
        return resultList;
    }
    private static BranchDto mapFromDbObjToBranchDto(Object[] obj){
        if(obj == null){
            return null;
        }
        BranchDto dto = new BranchDto();
        dto.setBranchId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setBranchName(obj[1] != null ? ObjectUtil.getValueOfString(obj[1]) : null);
        dto.setAddress(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setPhone(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setPartnerId(obj[4] != null ? ObjectUtil.getValueOfLong(obj[4]) : null);
        dto.setPartnerName(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        return dto;
    }
}
