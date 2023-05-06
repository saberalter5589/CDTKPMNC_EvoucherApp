package com.evoucherapp.evoucher.repository.impl;

import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.repository.BranchRepositoryCustom;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.QueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;

public class BranchRepositoryCustomImpl implements BranchRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchBranch(GetBranchListRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT br.branch_id, br.branch_name, br.address, br.phone, br.partner_id, pn.partner_name ");
        sql.append("FROM branch br ");
        sql.append("LEFT JOIN partner pn ON br.partner_id = pn.partner_id ");
        sql.append("WHERE br.is_deleted = false ");

        if(request.getBranchId() != null){
            sql.append("AND br.branch_id =:id");
            params.put("id", request.getBranchId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getBranchName())){
            sql.append("AND br.branch_name LIKE :branchName ");
            params.put("branchName", "%" + request.getBranchName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getPhone())){
            sql.append("AND br.phone LIKE :phone ");
            params.put("phone", "%" + request.getPhone() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getAddress())){
            sql.append("AND br.address LIKE :address ");
            params.put("address", "%" + request.getAddress() + "%");
        }

        if(request.getPartnerId() != null){
            sql.append("AND br.partner_id =:partnerId");
            params.put("partnerId", request.getPartnerId());
        }

        sql.append("ORDER BY br.updated_at DESC ");
        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
