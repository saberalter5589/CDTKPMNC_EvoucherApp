package com.evoucherapp.evoucher.repository.impl;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.request.vouchertemplate.SearchVoucherTemplateRequest;
import com.evoucherapp.evoucher.repository.VoucherTemplateRepositoryCustom;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.QueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;

public class VoucherTemplateRepositoryCustomImpl implements VoucherTemplateRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchVoucherTemplate(SearchVoucherTemplateRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT vtplt.voucher_template_id, vtplt.voucher_type_id, vt.voucher_type_code," +
                "vt.voucher_type_name, vtplt.campain_id, cp.campain_code, cp.campain_name, vtplt.voucher_template_code," +
                "vtplt.voucher_template_name, vtplt.amount, vtplt.description,vtplt.note," +
                "vtplt.date_start, vtplt.date_end, branch_info.branches, voucher_info.vouchers ");
        sql.append("FROM voucher_template vtplt ");
        sql.append("LEFT JOIN voucher_type vt ON vtplt.voucher_type_id = vt.voucher_type_id AND vt.is_deleted = false ");
        sql.append("LEFT JOIN campain cp ON cp.campain_id = vtplt.campain_id AND cp.is_deleted = false ");
        sql.append("LEFT JOIN (" +
                "SELECT " +
                "vtplt.voucher_template_id," +
                "string_agg(br.branch_id || '#'|| br.branch_name, ' , ') as branches " +
                "FROM voucher_template vtplt " +
                "LEFT JOIN branch_voucher bv ON vtplt.voucher_template_id = bv.voucher_template_id AND bv.is_deleted = false " +
                "LEFT JOIN branch br ON br.branch_id = bv.branch_id AND br.is_deleted = false " +
                "GROUP BY vtplt.voucher_template_id " +
                ") AS branch_info ON branch_info.voucher_template_id = vtplt.voucher_template_id ");
        sql.append("LEFT JOIN (" +
                "SELECT " +
                "vtplt.voucher_template_id, " +
                "string_agg(vc.voucher_id || '#'|| vc.voucher_code || '#' || vc.voucher_name, ' , ') as vouchers " +
                "FROM voucher_template vtplt " +
                "LEFT JOIN voucher vc ON vtplt.voucher_template_id = vc.voucher_template_id " +
                "GROUP BY vtplt.voucher_template_id " +
                ") AS voucher_info ON voucher_info.voucher_template_id = vtplt.voucher_template_id ");
        sql.append("WHERE vtplt.is_deleted = false ");

        if(request.getVoucherTemplateId() != null){
            sql.append("AND vtplt.voucher_template_id =:id ");
            params.put("id", request.getVoucherTemplateId());
        }

        if(request.getVoucherTypeId() != null){
            sql.append("AND vtplt.voucher_type_id =:voucherTypeId ");
            params.put("voucherTypeId", request.getVoucherTypeId());
        }

        if(request.getCampaignId() != null){
            sql.append("AND vtplt.campain_id =:campainId ");
            params.put("campainId", request.getCampaignId());
        }

        if(request.getPartnerId() != null){
            sql.append("AND cp.partner_id =:partnerId ");
            params.put("partnerId", request.getPartnerId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getVoucherTemplateCode())){
            sql.append("AND vtplt.voucher_template_code LIKE :templateCode ");
            params.put("templateCode", "%" + request.getVoucherTemplateCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getVoucherTemplateName())){
            sql.append("AND vtplt.voucher_template_name LIKE :templateName ");
            params.put("templateName", "%" + request.getVoucherTemplateName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getDateStart())){
            sql.append("AND vtplt.date_start >=:dateStart ");
            params.put("dateStart", DateTimeUtil.parseStringToDate(request.getDateStart(), DateTimeFormat.DATE));
        }

        if(!CommonUtil.isNullOrWhitespace(request.getDateEnd())){
            sql.append("AND vtplt.date_end <=:dateEnd ");
            params.put("dateEnd", DateTimeUtil.parseStringToDate(request.getDateEnd(), DateTimeFormat.DATE));
        }

        sql.append("GROUP BY vtplt.voucher_template_id, vtplt.voucher_type_id, vt.voucher_type_code,\n" +
                "                vt.voucher_type_name, vtplt.campain_id, cp.campain_code, cp.campain_name, vtplt.voucher_template_code,\n" +
                "                vtplt.voucher_template_name, vtplt.amount, vtplt.description,vtplt.note,\n" +
                "                vtplt.date_start, vtplt.date_end,\n" +
                "\t\t\t\tbranch_info.branches, voucher_info.vouchers ");
        sql.append("ORDER BY vtplt.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
