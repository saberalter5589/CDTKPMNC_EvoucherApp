package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.common.constant.CampaignStatus;
import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.obj.*;
import com.evoucherapp.evoucher.dto.obj.statistic.CampaignStatisticDto;
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

        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setUserId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        partnerDto.setPartnerName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setPartner(partnerDto);

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
                String gameDescription = infos[3];

                GameDto gameDto = new GameDto();
                gameDto.setId(Long.valueOf(gameId));
                gameDto.setCode(gameCode);
                gameDto.setName(gameName);
                gameDto.setDescription(gameDescription);
                dto.getGameList().add(gameDto);
            }
        }

        String voucherTemplateInfoStr = obj[11] != null ? ObjectUtil.getValueOfString(obj[11]) : null;
        if(!CommonUtil.isNullOrWhitespace(voucherTemplateInfoStr)){
            String[] voucherTemplateInfos = voucherTemplateInfoStr.split(" , ");
            for(String voucher : voucherTemplateInfos){
                String[] infos = voucher.split("#");
                String vtId = infos[0];
                String vtCode = infos[1];
                String vtName = infos[2];

                VoucherTemplateDto vtDto = new VoucherTemplateDto();
                vtDto.setVoucherTemplateId(Long.valueOf(vtId));
                vtDto.setVoucherTemplateCode(vtCode);
                vtDto.setVoucherTemplateName(vtName);
                dto.getVoucherTemplateDtoList().add(vtDto);
            }
        }
        return dto;
    }
}
