package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.AuthDto;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.entity.EUser;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.exception.UnAuthorizationException;
import com.evoucherapp.evoucher.repository.EUserRepository;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private EUserRepository eUserRepository;

    @Override
    @Transactional
    public void validateUser(BaseRequest request, List<Long> userTypeList) {
        AuthDto authDto = request.getAuthentication();
        EUser user = eUserRepository.findByUserIdAndPassword(authDto.getUserId(), authDto.getPassword());
        if(user == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

       if(!userTypeList.contains(user.getUserTypeId())){
           MessageInfo messageInfo = MessageUtil.formatMessage(10003);
           throw new UnAuthorizationException(messageInfo);
       }
    }
}
