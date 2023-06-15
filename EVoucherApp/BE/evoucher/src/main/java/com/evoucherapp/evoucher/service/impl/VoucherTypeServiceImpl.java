package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.VoucherTypeDto;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.CreateVoucherTypeRequest;
import com.evoucherapp.evoucher.dto.request.vouchertype.GetVoucherTypeListRequest;
import com.evoucherapp.evoucher.dto.response.vouchertype.CreateVoucherTypeResponse;
import com.evoucherapp.evoucher.dto.response.vouchertype.GetVoucherTypeListResponse;
import com.evoucherapp.evoucher.entity.VoucherType;
import com.evoucherapp.evoucher.exception.DataExistException;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.repository.VoucherTypeRepository;
import com.evoucherapp.evoucher.service.VoucherTypeService;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherTypeServiceImpl implements VoucherTypeService {
    @Autowired
    VoucherTypeRepository voucherTypeRepository;

    @Override
    @Transactional
    public CreateVoucherTypeResponse createVoucherType(CreateVoucherTypeRequest request) {
        Long userId = request.getAuthentication().getUserId();

        // Validate code
        VoucherType voucherType = voucherTypeRepository.findByCode(request.getVoucherTypeCode());
        if(voucherType != null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "voucher_type_code");
            throw new DataExistException(messageInfo);
        }

        VoucherType newVt = new VoucherType();
        newVt.setVoucherTypeCode(request.getVoucherTypeCode());
        newVt.setVoucherTypeName(request.getVoucherTypeName());
        newVt.setIsDeleted(false);
        EntityDxo.preCreate(userId, newVt);
        voucherTypeRepository.save(newVt);

        VoucherTypeDto dto = new VoucherTypeDto();
        dto.setId(newVt.getVoucherTypeId());
        dto.setCode(newVt.getVoucherTypeCode());
        dto.setName(newVt.getVoucherTypeName());

        CreateVoucherTypeResponse response = new CreateVoucherTypeResponse();
        response.setVoucherTypeDto(dto);
        return response;
    }

    @Override
    public GetVoucherTypeListResponse searchVoucherTypeList(GetVoucherTypeListRequest request) {
        List<VoucherType> allVts = voucherTypeRepository.findAll();
        List<VoucherTypeDto> dtos = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(allVts)){
            for(VoucherType vt : allVts){
                VoucherTypeDto dto = new VoucherTypeDto();
                dto.setId(vt.getVoucherTypeId());
                dto.setCode(vt.getVoucherTypeCode());
                dto.setName(vt.getVoucherTypeName());
                dtos.add(dto);
            }
        }
        GetVoucherTypeListResponse response = new GetVoucherTypeListResponse();
        response.setVoucherTypeDtoList(dtos);
        return response;
    }

    @Override
    @Transactional
    public void updateVoucherType(Long id, CreateVoucherTypeRequest request) {
        Long userId = request.getAuthentication().getUserId();
        VoucherType voucherType = voucherTypeRepository.findByVoucherTypeId(id);
        if(voucherType == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "voucher_type_code");
            throw new DataExistException(messageInfo);
        }
        voucherType.setVoucherTypeCode(request.getVoucherTypeCode());
        voucherType.setVoucherTypeName(request.getVoucherTypeName());
        EntityDxo.preUpdate(userId, voucherType);
        voucherTypeRepository.save(voucherType);
    }

    @Override
    @Transactional
    public void deleteVoucherType(Long id, BaseRequest request) {
        Long userId = request.getAuthentication().getUserId();
        VoucherType voucherType = voucherTypeRepository.findByVoucherTypeId(id);
        if(voucherType == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "voucher_type_code");
            throw new DataExistException(messageInfo);
        }
        voucherType.setIsDeleted(true);
        EntityDxo.preUpdate(userId, voucherType);
        voucherTypeRepository.save(voucherType);
    }
}
