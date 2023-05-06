package com.evoucherapp.evoucher.util;

import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.Map;

public class QueryUtil {
    public static void setParamsToQuery(Query query, HashMap<String, Object> params){
        if(!CommonUtil.isNullOrEmpty(params)){
            for (Map.Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
    }
}
