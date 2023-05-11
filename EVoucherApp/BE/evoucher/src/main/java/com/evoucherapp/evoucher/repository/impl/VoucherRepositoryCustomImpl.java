package com.evoucherapp.evoucher.repository.impl;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.voucher.SearchVoucherRequest;
import com.evoucherapp.evoucher.repository.VoucherRepositoryCustom;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.QueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VoucherRepositoryCustomImpl implements VoucherRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchVoucher(Long userId, Long userType, SearchVoucherRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT " +
                "vc.voucher_id," +
                "vc.customer_id," +
                "cm.customer_name," +
                "vc.voucher_template_id," +
                "vt.voucher_template_code," +
                "vt.voucher_template_name," +
                "vt.date_start," +
                "vt.date_end," +
                "vc.voucher_code," +
                "vc.voucher_name," +
                "vc.description," +
                "vc.is_valid," +
                "vc.is_used," +
                "pn.partner_id," +
                "pn.partner_name," +
                "pnt.partner_type_code," +
                "pnt.partner_type_name," +
                "branch_info.branches ");
        sql.append("FROM voucher vc ");
        sql.append("LEFT JOIN voucher_template vt ON vc.voucher_template_id = vt.voucher_template_id AND vt.is_deleted = false ");
        sql.append("LEFT JOIN campain cp ON cp.campain_id = vt.campain_id AND cp.is_deleted = false ");
        sql.append("LEFT JOIN customer cm ON cm.customer_id = vc.customer_id AND cm.is_deleted = false ");
        sql.append("LEFT JOIN partner pn ON pn.partner_id = cp.partner_id AND pn.is_deleted = false ");
        sql.append("LEFT JOIN partner_type pnt ON pn.partner_type_id = pnt.partner_type_id AND pnt.is_deleted = false ");
        sql.append("LEFT JOIN ( " +
                "SELECT " +
                "vc.voucher_id," +
                "string_agg(br.branch_id || '#'|| br.branch_name, ' , ') as branches " +
                "FROM voucher vc " +
                "LEFT JOIN branch_voucher bv ON vc.voucher_template_id = bv.voucher_template_id AND bv.is_deleted = false " +
                "LEFT JOIN branch br ON bv.branch_id = br.branch_id AND br.is_deleted = false " +
                "GROUP BY vc.voucher_id " +
                ") AS branch_info ON branch_info.voucher_id = vc.voucher_id ");
        sql.append("WHERE vc.is_deleted = false ");

        if (Objects.equals(userType, UserType.PARTNER)) {
            sql.append("AND pn.partner_id =:partnerId ");
            params.put("partnerId", userId);
        }

        if(request.getVoucherId() != null){
            sql.append("AND vc.voucher_id =:id ");
            params.put("id", request.getVoucherId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getVoucherCode())){
            sql.append("AND vc.voucher_code LIKE :voucherCode ");
            params.put("voucherCode", "%" + request.getVoucherCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getVoucherName())){
            sql.append("AND vc.voucher_name LIKE :voucherName ");
            params.put("voucherName", "%" + request.getVoucherName() + "%");
        }

        if(request.getCustomerId() != null){
            sql.append("AND vc.customer_id =:customerId ");
            params.put("customerId", request.getCustomerId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getCustomerName())){
            sql.append("AND cm.customer_name LIKE :customerName ");
            params.put("customerName", "%" + request.getCustomerName() + "%");
        }

        if(request.getVTemplateId() != null){
            sql.append("AND vc.voucher_template_id =:vTemplateId ");
            params.put("vTemplateId", request.getVTemplateId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getVTemplateCode())){
            sql.append("AND vt.voucher_template_code LIKE :vTemplateCode ");
            params.put("vTemplateCode", "%" + request.getVTemplateCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getVTemplateName())){
            sql.append("AND vt.voucher_template_name LIKE :vTemplateName ");
            params.put("vTemplateName", "%" + request.getVTemplateName() + "%");
        }

        sql.append("ORDER BY vc.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
