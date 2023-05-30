package com.evoucherapp.evoucher.repository.impl;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.user.GetCustomerListRequest;
import com.evoucherapp.evoucher.dto.request.user.GetPartnerListRequest;
import com.evoucherapp.evoucher.repository.EUserRepositoryCustom;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.QueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;

public class EUserRepositoryCustomImpl implements EUserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchCustomer(GetCustomerListRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT eu.user_id, eu.user_type_id, eu.user_name, eu.email, eu.phone, eu.address, ");
        sql.append("cr.customer_name, cr.birthday ");
        sql.append("FROM e_user eu ");
        sql.append("LEFT JOIN customer cr ON eu.user_id = cr.customer_id ");
        sql.append("WHERE eu.is_deleted = false AND cr.is_deleted = false AND eu.user_type_id =:userTypeId ");
        params.put("userTypeId", UserType.CUSTOMER);

        if(request.getId() != null){
            sql.append("AND eu.user_id =:id ");
            params.put("id", Long.valueOf(request.getId()));
        }

        if(!CommonUtil.isNullOrWhitespace(request.getUsername())){
            sql.append("AND eu.user_name LIKE :userName ");
            params.put("userName", "%" + request.getUsername() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getCustomerName())){
            sql.append("AND cr.customer_name LIKE :customerName ");
            params.put("customerName", "%" + request.getCustomerName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getEmail())){
            sql.append("AND eu.email LIKE :email ");
            params.put("email", "%" + request.getEmail() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getAddress())){
            sql.append("AND eu.address LIKE :address ");
            params.put("address", "%" + request.getAddress() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getBirthday())){
            sql.append("AND cr.birthday =:birthday ");
            params.put("birthday", DateTimeUtil.parseStringToDate(request.getBirthday(), DateTimeFormat.DATE));
        }

        sql.append("ORDER BY eu.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }

    @Override
    public List<Object[]> searchPartner(GetPartnerListRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT eu.user_id, eu.user_type_id, eu.user_name, eu.email, eu.phone, eu.address, ");
        sql.append("pn.partner_type_id, pn.partner_name, pn.note ");
        sql.append("FROM e_user eu ");
        sql.append("LEFT JOIN partner pn ON eu.user_id = pn.partner_id ");
        sql.append("WHERE eu.is_deleted = false AND pn.is_deleted = false AND eu.user_type_id =:userTypeId ");
        params.put("userTypeId", UserType.PARTNER);
        if(request.getId() != null){
            sql.append("AND eu.user_id =:id ");
            params.put("id", Long.valueOf(request.getId()));
        }

        if(!CommonUtil.isNullOrWhitespace(request.getUsername())){
            sql.append("AND eu.user_name LIKE :userName ");
            params.put("userName", "%" + request.getUsername() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getPartnerName())){
            sql.append("AND pn.partner_name LIKE :partnerName ");
            params.put("partnerName", "%" + request.getPartnerName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getEmail())){
            sql.append("AND eu.email LIKE :email ");
            params.put("email", "%" + request.getEmail() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getAddress())){
            sql.append("AND eu.address LIKE :address ");
            params.put("address", "%" + request.getAddress() + "%");
        }

        if(request.getPartnerTypeId() != null){
            sql.append("AND eu.partner_type_id =:partnerTypeId ");
            params.put("partnerTypeId", request.getPartnerTypeId());
        }

        sql.append("ORDER BY eu.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
