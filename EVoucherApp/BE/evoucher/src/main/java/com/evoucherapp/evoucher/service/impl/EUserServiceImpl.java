package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.CustomerDto;
import com.evoucherapp.evoucher.dto.obj.PartnerDto;
import com.evoucherapp.evoucher.dto.obj.UserDto;
import com.evoucherapp.evoucher.dto.request.user.*;
import com.evoucherapp.evoucher.dto.response.user.CreateUserResponse;
import com.evoucherapp.evoucher.dto.response.user.GetCustomerListResponse;
import com.evoucherapp.evoucher.dto.response.user.GetPartnerListResponse;
import com.evoucherapp.evoucher.entity.Customer;
import com.evoucherapp.evoucher.entity.EUser;
import com.evoucherapp.evoucher.entity.Partner;
import com.evoucherapp.evoucher.entity.PartnerType;
import com.evoucherapp.evoucher.exception.DataExistException;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.exception.UnAuthorizationException;
import com.evoucherapp.evoucher.mapper.CustomerDxo;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.mapper.PartnerDxo;
import com.evoucherapp.evoucher.mapper.UserDxo;
import com.evoucherapp.evoucher.repository.CustomerRepository;
import com.evoucherapp.evoucher.repository.EUserRepository;
import com.evoucherapp.evoucher.repository.PartnerRepository;
import com.evoucherapp.evoucher.repository.PartnerTypeRepository;
import com.evoucherapp.evoucher.service.EUserService;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EUserServiceImpl implements EUserService {

    @Autowired
    EUserRepository eUserRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    PartnerTypeRepository partnerTypeRepository;

    @Override
    @Transactional
    public CreateUserResponse createNewUser(CreateUserRequest request) {
        if(!Objects.equals(request.getUserTypeId(), UserType.PARTNER) && !Objects.equals(request.getUserTypeId(), UserType.CUSTOMER)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "UserTypeId");
            throw new NoDataFoundException(messageInfo);
        }

        // Check if user (username) exist or not
        EUser user = eUserRepository.findByUserName(request.getUserName());
        if(user != null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "username");
            throw new DataExistException(messageInfo);
        }

        // Create new user
        EUser newUser = UserDxo.mapFromCreateRequestToEntity(request);
        newUser.setIsDeleted(false);
        eUserRepository.save(newUser);
        Long userId = newUser.getUserId();
        EntityDxo.preCreate(userId, newUser);

        if(Objects.equals(request.getUserTypeId(), UserType.CUSTOMER)){
            createCustomer(userId, (CreateCustomerRequest) request);
        } else {
            createPartner(userId, (CreatePartnerRequest) request);
        }
        CreateUserResponse response = new CreateUserResponse();
        UserDto userDto = UserDxo.mapFromEntityToDTO(newUser);
        response.setUser(userDto);
        return response;
    }

    @Override
    public GetCustomerListResponse searchCustomer(GetCustomerListRequest request) {
        List<Object[]> dbCustomerList = eUserRepository.searchCustomer(request);
        List<CustomerDto> customerDtoList = CustomerDxo.mapFromDbObjListToCustomerDtoList(dbCustomerList);
        GetCustomerListResponse response = new GetCustomerListResponse();
        response.setCustomerList(customerDtoList);
        return response;
    }

    @Override
    public GetPartnerListResponse searchPartner(GetPartnerListRequest request) {
        List<Object[]> dbPartnerList = eUserRepository.searchPartner(request);
        List<PartnerDto> partnerDtoList = PartnerDxo.mapFromDbObjListToPartnerDtoList(dbPartnerList);
        GetPartnerListResponse response = new GetPartnerListResponse();
        response.setPartnerList(partnerDtoList);
        return response;
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UpdateUserRequest request) {
        Long currentUserId = request.getAuthentication().getUserId();
        if(!userId.equals(currentUserId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        EUser user = eUserRepository.findByUserId(currentUserId);
        if(user == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "id");
            throw new NoDataFoundException(messageInfo);
        }
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        EntityDxo.preUpdate(currentUserId, user);
        eUserRepository.save(user);

        if(Objects.equals(request.getUserTypeId(), UserType.CUSTOMER)){
            updateCustomer(userId, (UpdateCustomerRequest) request);
        } else {
            updatePartner(userId, (UpdatePartnerRequest) request);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId, DeleteUserRequest request) {
        Long currentUserId = request.getAuthentication().getUserId();
        if(!userId.equals(currentUserId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        EUser user = eUserRepository.findByUserId(currentUserId);
        if(user == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "id");
            throw new NoDataFoundException(messageInfo);
        }
        user.setIsDeleted(true);
        EntityDxo.preUpdate(currentUserId, user);
        eUserRepository.save(user);

        if(Objects.equals(request.getUserTypeId(), UserType.CUSTOMER)){
            Customer customer = customerRepository.findByUserId(userId);
            customer.setIsDeleted(true);
            EntityDxo.preUpdate(currentUserId, customer);
            customerRepository.save(customer);
        } else {
            Partner partner = partnerRepository.findByPartnerId(userId);
            partner.setIsDeleted(true);
            EntityDxo.preUpdate(currentUserId, partner);
            partnerRepository.save(partner);
        }
    }

    private void updateCustomer(Long userId, UpdateCustomerRequest request){
        Customer customer = customerRepository.findByUserId(userId);
        customer.setCustomerName(request.getCustomerName());
        LocalDate birthDay = DateTimeUtil.parseStringToDate(request.getBirthday(), DateTimeFormat.DATE);
        customer.setBirthday(birthDay);
        EntityDxo.preUpdate(userId, customer);
        customerRepository.save(customer);
    }

    private void updatePartner(Long userId, UpdatePartnerRequest request){
        Partner partner = partnerRepository.findByPartnerId(userId);
        partner.setPartnerName(request.getPartnerName());
        processUpdatePartnerType(partner, request.getPartnerTypeId());
        partner.setNote(request.getNote());
        EntityDxo.preUpdate(userId, partner);
        partnerRepository.save(partner);
    }

    private void createCustomer(Long userId, CreateCustomerRequest request){
        LocalDate birthDay = DateTimeUtil.parseStringToDate(request.getBirthday(), DateTimeFormat.DATE);
        Customer customer = new Customer();
        customer.setCustomerId(userId);
        customer.setCustomerName(request.getCustomerName());
        customer.setBirthday(birthDay);
        customer.setIsDeleted(false);
        EntityDxo.preCreate(userId, customer);
        customerRepository.save(customer);
    }

    private void createPartner(Long userId, CreatePartnerRequest request){
        Partner partner = new Partner();
        partner.setPartnerId(userId);
        processUpdatePartnerType(partner, request.getPartnerTypeId());
        partner.setPartnerName(request.getPartnerName());
        partner.setNote(request.getNote());
        partner.setIsDeleted(false);
        EntityDxo.preCreate(userId, partner);
        partnerRepository.save(partner);
    }

    private void processUpdatePartnerType(Partner partner, Long partnerTypeId){
        if(partnerTypeId == null){
            partner.setPartnerTypeId(0L);
        }
        else {
            PartnerType partnerType = partnerTypeRepository.findByPartnerId(partnerTypeId);
            if(partnerType == null){
                MessageInfo messageInfo = MessageUtil.formatMessage(10001, "partnerTypeId");
                throw new NoDataFoundException(messageInfo);
            }else{
                partner.setPartnerTypeId(partnerType.getPartnerTypeId());
            }
        }
    }
}
