package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.obj.BranchDto;
import com.evoucherapp.evoucher.dto.obj.CampaignDto;
import com.evoucherapp.evoucher.dto.obj.GameDto;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CampaignDxo {
    public static List<CampaignDto> convertFromDbListToDtoList(List<Object[]> dbObjList){
        List<CampaignDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToCampaignDto(obj));
            }
        }
        return resultList;
    }

    private static CampaignDto mapFromDbObjToCampaignDto(Object[] obj){
        if(obj == null){
            return null;
        }
        CampaignDto dto = new CampaignDto();
        dto.setCampainId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);
        dto.setPartnerId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        dto.setPartnerName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setCampainCode(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setCampainName(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        dto.setDateStart(obj[5] != null ? DateTimeUtil.convertFromDateToString((Date)obj[5], DateTimeFormat.DATE): null);
        dto.setDateEnd(obj[6] != null ? DateTimeUtil.convertFromDateToString((Date)obj[6], DateTimeFormat.DATE): null);
        dto.setNote(obj[7] != null ? ObjectUtil.getValueOfString(obj[7]) : null);
        dto.setDescription(obj[8] != null ? ObjectUtil.getValueOfString(obj[8]) : null);
        dto.setStatus(obj[9] != null ? ObjectUtil.getValueOfLong(obj[9]) : null);

        String gameInfoStr = obj[10] != null ? ObjectUtil.getValueOfString(obj[10]) : null;
        if(!CommonUtil.isNullOrWhitespace(gameInfoStr)){
            String[] gameInfos = gameInfoStr.split(" , ");
            for(String gameInfo : gameInfos){
                String[] infos = gameInfo.split("#");
                String gameId = infos[0];
                String gameCode = infos[1];
                String gameName = infos[2];

                GameDto gameDto = new GameDto();
                gameDto.setId(Long.valueOf(gameId));
                gameDto.setCode(gameCode);
                gameDto.setName(gameName);
                dto.getGameList().add(gameDto);
            }
        }
        return dto;
    }


}