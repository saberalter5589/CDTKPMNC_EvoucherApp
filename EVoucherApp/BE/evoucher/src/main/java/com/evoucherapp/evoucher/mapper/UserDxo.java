package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.dto.obj.UserDto;
import com.evoucherapp.evoucher.dto.request.user.CreateUserRequest;
import com.evoucherapp.evoucher.entity.EUser;

public class UserDxo {
    public static EUser mapFromCreateRequestToEntity(CreateUserRequest request){
        EUser eUser = new EUser();
        eUser.setUserName(request.getUserName());
        eUser.setPassword(request.getPassword());
        eUser.setEmail(request.getEmail());
        eUser.setPhone(request.getPhone());
        eUser.setAddress(request.getAddress());
        eUser.setUserTypeId(request.getUserTypeId());
        return eUser;
    }

    public static UserDto mapFromEntityToDTO(EUser eUser){
        UserDto userDto = new UserDto();
        userDto.setUserId(eUser.getUserId());
        userDto.setUserTypeId(eUser.getUserTypeId());
        return userDto;
    }
}
