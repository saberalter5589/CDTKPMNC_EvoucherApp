package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.dto.obj.CampaignDto;
import com.evoucherapp.evoucher.dto.obj.statistic.CampaignStatisticDto;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class CampaignStatisticDxo {
    public static List<CampaignStatisticDto> convertFromDbListToDtoList(List<Object[]> dbObjList){
        List<CampaignStatisticDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToDto(obj));
            }
        }
        return resultList;
    }

    private static CampaignStatisticDto mapFromDbObjToDto(Object[] obj){
        if(obj == null){
            return null;
        }
        CampaignStatisticDto dto = new CampaignStatisticDto();
        dto.setCampaignId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setCampaignCode(obj[1] != null ? ObjectUtil.getValueOfString(obj[1]) : null);
        dto.setCampaignName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setTotalCustomer(obj[3] != null ? ObjectUtil.getValueOfLong(obj[3]) : null);
        dto.setTotalVoucher(obj[4] != null ? ObjectUtil.getValueOfLong(obj[4]) : null);
        dto.setTotalUsedVoucher(obj[5] != null ? ObjectUtil.getValueOfLong(obj[5]) : null);
        return dto;
    }
}
