package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.dto.obj.PartnerTypeDto;
import com.evoucherapp.evoucher.dto.obj.VoucherTypeDto;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.partnertype.CreatePartnerTypeRequest;
import com.evoucherapp.evoucher.dto.request.partnertype.SearchPartnerTypeRequest;
import com.evoucherapp.evoucher.dto.response.partnertype.CreatePartnerTypeResponse;
import com.evoucherapp.evoucher.dto.response.partnertype.SearchPartnerTypeResponse;
import com.evoucherapp.evoucher.entity.PartnerType;
import com.evoucherapp.evoucher.entity.VoucherType;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.repository.PartnerTypeRepository;
import com.evoucherapp.evoucher.service.PartnerTypeService;
import com.evoucherapp.evoucher.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerTypeServiceImpl implements PartnerTypeService {

    @Autowired
    PartnerTypeRepository partnerTypeRepository;

    @Override
    @Transactional
    public CreatePartnerTypeResponse createPartnerType(CreatePartnerTypeRequest request) {
        Long userId = request.getAuthentication().getUserId();

        PartnerType partnerType = new PartnerType();
        partnerType.setPartnerTypeCode(request.getPartnerTypeCode());
        partnerType.setPartnerTypeName(request.getPartnerTypeName());
        partnerType.setIsDeleted(false);
        EntityDxo.preCreate(userId, partnerType);
        partnerTypeRepository.save(partnerType);

        PartnerTypeDto dto = new PartnerTypeDto();
        dto.setPartnerTypeId(partnerType.getPartnerTypeId());
        dto.setPartnerTypeCode(partnerType.getPartnerTypeCode());
        dto.setPartnerTypeName(partnerType.getPartnerTypeName());

        CreatePartnerTypeResponse response = new CreatePartnerTypeResponse();
        response.setPartnerTypeDto(dto);
        return response;
    }

    @Override
    public SearchPartnerTypeResponse searchPartnerType(SearchPartnerTypeRequest request) {
        List<PartnerType> allPartnerTypes = partnerTypeRepository.findAll();
        List<PartnerTypeDto> dtos = new ArrayList<>();
        if(!CommonUtil.isNullOrEmpty(allPartnerTypes)){
            for(PartnerType vt : allPartnerTypes){
                PartnerTypeDto dto = new PartnerTypeDto();
                dto.setPartnerTypeId(vt.getPartnerTypeId());
                dto.setPartnerTypeCode(vt.getPartnerTypeCode());
                dto.setPartnerTypeName(vt.getPartnerTypeName());
                dtos.add(dto);
            }
        }
        SearchPartnerTypeResponse response = new SearchPartnerTypeResponse();
        response.setPartnerTypeDtoList(dtos);
        return response;
    }

    @Override
    @Transactional
    public void updatePartnerType(Long id, CreatePartnerTypeRequest request) {
        Long userId = request.getAuthentication().getUserId();
        PartnerType partnerType = partnerTypeRepository.findByPartnerTypeId(id);
        if(partnerType == null){
            return;
        }
        partnerType.setPartnerTypeCode(request.getPartnerTypeCode());
        partnerType.setPartnerTypeName(request.getPartnerTypeName());
        EntityDxo.preUpdate(userId, partnerType);
        partnerTypeRepository.save(partnerType);
    }

    @Override
    @Transactional
    public void deletePartnerType(Long id, BaseRequest request) {
        Long userId = request.getAuthentication().getUserId();
        PartnerType partnerType = partnerTypeRepository.findByPartnerTypeId(id);
        if(partnerType == null){
            return;
        }
        partnerType.setIsDeleted(true);
        EntityDxo.preUpdate(userId, partnerType);
        partnerTypeRepository.save(partnerType);
    }
}
