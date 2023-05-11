package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.obj.*;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoucherDxo {
    public static List<VoucherDto> convertFromDbListToDtoList(List<Object[]> dbObjList){
        List<VoucherDto> resultList = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(dbObjList)){
            for(Object[] obj : dbObjList){
                resultList.add(mapFromDbObjToDto(obj));
            }
        }
        return resultList;
    }
    private static VoucherDto mapFromDbObjToDto(Object[] obj){
        if(obj == null){
            return null;
        }

        VoucherDto dto = new VoucherDto();
        dto.setVoucherId(obj[0] != null ? ObjectUtil.getValueOfLong(obj[0]) : null);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setUserId(obj[1] != null ? ObjectUtil.getValueOfLong(obj[1]) : null);
        customerDto.setCustomerName(obj[2] != null ? ObjectUtil.getValueOfString(obj[2]) : null);
        dto.setCustomer(customerDto);

        VoucherTemplateDto voucherTemplateDto = new VoucherTemplateDto();
        voucherTemplateDto.setVoucherTemplateId(obj[3] != null ? ObjectUtil.getValueOfLong(obj[3]) : null);
        voucherTemplateDto.setVoucherTemplateCode(obj[4] != null ? ObjectUtil.getValueOfString(obj[4]) : null);
        voucherTemplateDto.setVoucherTemplateName(obj[5] != null ? ObjectUtil.getValueOfString(obj[5]) : null);
        dto.setVoucherTemplate(voucherTemplateDto);

        dto.setDateStart(obj[6] != null ? DateTimeUtil.convertFromDateToString((Date)obj[6], DateTimeFormat.DATE): null);
        dto.setDateEnd(obj[7] != null ? DateTimeUtil.convertFromDateToString((Date)obj[7], DateTimeFormat.DATE): null);

        dto.setVoucherCode(obj[8] != null ? ObjectUtil.getValueOfString(obj[8]) : null);
        dto.setVoucherName(obj[9] != null ? ObjectUtil.getValueOfString(obj[9]) : null);
        dto.setDescription(obj[10] != null ? ObjectUtil.getValueOfString(obj[10]) : null);
        dto.setIsValid(obj[11] != null ? ObjectUtil.getValueOfBoolean(obj[11]) : null);
        dto.setIsUsed(obj[12] != null ? ObjectUtil.getValueOfBoolean(obj[12]) : null);

        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setUserId(obj[13] != null ? ObjectUtil.getValueOfLong(obj[13]) : null);
        partnerDto.setPartnerName(obj[14] != null ? ObjectUtil.getValueOfString(obj[14]) : null);
        partnerDto.setPartnerTypeCode(obj[15] != null ? ObjectUtil.getValueOfString(obj[15]) : null);
        partnerDto.setPartnerTypeName(obj[16] != null ? ObjectUtil.getValueOfString(obj[16]) : null);
        dto.setPartner(partnerDto);

        String branchInfoStr = obj[17] != null ? ObjectUtil.getValueOfString(obj[17]) : null;
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

        return dto;
    }
}
