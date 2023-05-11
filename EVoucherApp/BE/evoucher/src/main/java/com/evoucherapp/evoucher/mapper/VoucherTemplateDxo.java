package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.obj.*;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoucherTemplateDxo {
    public static List<VoucherTemplateDto> convertFromDbListToDtoList(List<Object[]> dbObjList){
        List<VoucherTemplateDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToDto(obj));
            }
        }
        return resultList;
    }
    private static VoucherTemplateDto mapFromDbObjToDto(Object[] obj){
        if(obj == null){
            return null;
        }

        VoucherTemplateDto dto = new VoucherTemplateDto();
        dto.setVoucherTemplateId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);

        VoucherTypeDto voucherTypeDto = new VoucherTypeDto();
        voucherTypeDto.setId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        voucherTypeDto.setCode(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        voucherTypeDto.setName(obj[3] != null ? ObjectUtil.getValueOfString(obj[3]) : null);
        dto.setVoucherType(voucherTypeDto);

        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCampainId(obj[4] != null ? ObjectUtil.getValueOfLong(obj[4]) : null);
        campaignDto.setCampainCode(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        campaignDto.setCampainName(obj[6] != null ? ObjectUtil.getValueOfString(obj[6]) : null);
        dto.setCampaign(campaignDto);

        dto.setVoucherTemplateCode(obj[7] != null ? ObjectUtil.getValueOfString(obj[7]) : null);
        dto.setVoucherTemplateName(obj[8] != null ? ObjectUtil.getValueOfString(obj[8]) : null);
        dto.setAmount(obj[9] != null ? ObjectUtil.getValueOfLong(obj[9]) : null);
        dto.setDescription(obj[10] != null ? ObjectUtil.getValueOfString(obj[10]) : null);
        dto.setNote(obj[11] != null ? ObjectUtil.getValueOfString(obj[11]) : null);

        dto.setDateStart(obj[12] != null ? DateTimeUtil.convertFromDateToString((Date)obj[12], DateTimeFormat.DATE): null);
        dto.setDateEnd(obj[13] != null ? DateTimeUtil.convertFromDateToString((Date)obj[13], DateTimeFormat.DATE): null);

        String branchInfoStr = obj[14] != null ? ObjectUtil.getValueOfString(obj[14]) : null;
        if(!CommonUtil.isNullOrWhitespace(branchInfoStr)){
            String[] branchInfos = branchInfoStr.split(" , ");
            for(String branchInfo : branchInfos){
                String[] infos = branchInfo.split("#");
                String branchId = infos[0];
                String branchName = infos[1];

                BranchDto branchDto = new BranchDto();
                branchDto.setBranchId(Long.valueOf(branchId));
                branchDto.setBranchName(branchName);
                dto.getBranchList().add(branchDto);
            }
        }

        String voucherInfoStr = obj[15] != null ? ObjectUtil.getValueOfString(obj[15]) : null;
        if(!CommonUtil.isNullOrWhitespace(voucherInfoStr)){
            String[] voucherInfos = voucherInfoStr.split(" , ");
            for(String voucherInfo : voucherInfos){
                String[] infos = voucherInfo.split("#");
                String voucherId = infos[0];
                String voucherCode = infos[1];
                String voucherName = infos[2];

                VoucherDto voucherDto = new VoucherDto();
                voucherDto.setVoucherId(Long.valueOf(voucherId));
                voucherDto.setVoucherCode(voucherCode);
                voucherDto.setVoucherName(voucherName);
                dto.getVoucherList().add(voucherDto);
            }
        }

        return dto;
    }
}
