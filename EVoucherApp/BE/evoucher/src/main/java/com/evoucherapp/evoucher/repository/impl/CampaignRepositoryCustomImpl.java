package com.evoucherapp.evoucher.repository.impl;

import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignStatisticRequest;
import com.evoucherapp.evoucher.repository.CampaignRepositoryCustom;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.QueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.List;

public class CampaignRepositoryCustomImpl implements CampaignRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> searchCampaign(SearchCampaignRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT cp.campain_id,cp.partner_id, ptn.partner_name," +
                "cp.campain_code,cp.campain_name,cp.date_start,cp.date_end,cp.note," +
                "cp.description,cp.status, game_info.games, v_template_info.voucher_template ");
        sql.append("FROM campain cp ");
        sql.append("LEFT JOIN partner ptn ON ptn.partner_id = cp.partner_id AND ptn.is_deleted = false ");
        sql.append("LEFT JOIN (" +
                "SELECT " +
                "cp.campain_id," +
                "string_agg(gm.game_id || '#'|| gm.game_code || '#' || gm.game_name || '#' || gm.description, ' , ') as games " +
                "FROM campain cp " +
                "LEFT JOIN campain_game cpg ON cp.campain_id = cpg.campain_id AND cpg.is_deleted = false " +
                "LEFT JOIN game gm ON gm.game_id = cpg.game_id " +
                "GROUP BY cp.campain_id " +
                ") AS game_info ON cp.campain_id = game_info.campain_id ");
        sql.append("LEFT JOIN ( " +
                "SELECT " +
                "cp.campain_id, " +
                "string_agg(vtplt.voucher_template_id || '#'|| vtplt.voucher_template_code || '#' || vtplt.voucher_template_name, ' , ') as voucher_template\n" +
                "FROM campain cp " +
                "LEFT JOIN voucher_template vtplt ON cp.campain_id = vtplt.campain_id AND vtplt.is_deleted = false " +
                "GROUP BY cp.campain_id " +
                ")AS v_template_info ON cp.campain_id = v_template_info.campain_id ");
        sql.append("WHERE cp.is_deleted = false ");

        if(request.getCampainId() != null){
            sql.append("AND cp.campain_id =:id ");
            params.put("id", request.getCampainId());
        }

        if(request.getPartnerId() != null){
            sql.append("AND ptn.partner_id =:partnerId ");
            params.put("partnerId", request.getPartnerId());
        }

        if(!CommonUtil.isNullOrWhitespace(request.getCampaignCode())){
            sql.append("AND cp.campain_code LIKE :campainCode ");
            params.put("campainCode", "%" + request.getCampaignCode() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getCampainName())){
            sql.append("AND cp.campain_name LIKE :campainName ");
            params.put("campainName", "%" + request.getCampainName() + "%");
        }

        if(!CommonUtil.isNullOrWhitespace(request.getDateStart())){
            sql.append("AND cp.date_start >=:dateStart ");
            params.put("dateStart", DateTimeUtil.parseStringToDate(request.getDateStart(), DateTimeFormat.DATE));
        }

        if(!CommonUtil.isNullOrWhitespace(request.getDateEnd())){
            sql.append("AND cp.date_end <=:dateEnd ");
            params.put("dateEnd", DateTimeUtil.parseStringToDate(request.getDateEnd(), DateTimeFormat.DATE));
        }

        if(request.getStatus() != null){
            sql.append("AND cp.status =:status ");
            params.put("status", request.getStatus());
        }

        sql.append("GROUP BY cp.campain_id,  cp.partner_id,ptn.partner_name,cp.campain_code,cp.campain_name,cp.date_start,cp.date_end,cp.description,cp.note,cp.status, " +
                " game_info.games,v_template_info.voucher_template ");
        sql.append("ORDER BY cp.updated_at DESC ");

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }

    @Override
    public List<Object[]> searchCampaignStatistic(SearchCampaignStatisticRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT \n" +
                "cp.campain_id,\n" +
                "cp.campain_code,\n" +
                "cp.campain_name,\n" +
                "coalesce(cust_info.cust_total,0) as cust_total,\n" +
                "coalesce(voucher_info.voucher_total,0) as voucher_total,\n" +
                "coalesce(voucher_info_1.voucher_used_total,0) as voucher_used_total\n" +
                "FROM campain cp\n" +
                "LEFT JOIN (\n" +
                "\tSELECT cc.campain_id, COUNT(cc.customer_id) AS cust_total\n" +
                "\tFROM campain_customer cc\n" +
                "\tWHERE cc.is_deleted = false\n" +
                "\tGROUP BY cc.campain_id\n" +
                "\tORDER BY cc.campain_id\n" +
                ") AS cust_info ON cp.campain_id = cust_info.campain_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT vt.campain_id, COUNT(vc.voucher_id) AS voucher_total\n" +
                "\tFROM voucher_template vt\n" +
                "\tLEFT JOIN voucher vc ON vt.voucher_template_id = vc.voucher_template_id \n" +
                "\tWHERE vt.is_deleted = false AND vc.is_deleted = false\n" +
                "\tGROUP BY vt.campain_id\n" +
                "\tORDER BY vt.campain_id\n" +
                ") AS voucher_info ON cp.campain_id = voucher_info.campain_id\n" +
                "LEFT JOIN (\n" +
                "\tSELECT vt.campain_id, COUNT(vc.voucher_id) AS voucher_used_total\n" +
                "\tFROM voucher_template vt\n" +
                "\tLEFT JOIN voucher vc ON vt.voucher_template_id = vc.voucher_template_id \n" +
                "\tWHERE vt.is_deleted = false AND vc.is_deleted = false AND vc.is_used = true\n" +
                "\tGROUP BY vt.campain_id\n" +
                "\tORDER BY vt.campain_id\n" +
                ") AS voucher_info_1 ON cp.campain_id = voucher_info_1.campain_id\n" +
                "WHERE cp.is_deleted = false ");

        sql.append("AND cp.partner_id =:partnerId ");
        params.put("partnerId", request.getPartnerId());

        if(request.getCampainId() != null){
            sql.append("AND cp.campain_id =:id ");
            params.put("id", request.getCampainId());
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.setParamsToQuery(query, params);
        List<Object[]> dbResult = query.getResultList();
        return dbResult;
    }
}
