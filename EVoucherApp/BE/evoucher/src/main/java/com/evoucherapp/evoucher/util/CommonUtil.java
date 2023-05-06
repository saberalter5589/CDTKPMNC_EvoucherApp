package com.evoucherapp.evoucher.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CommonUtil {
    public static boolean isNullOr(Object obj){
        return obj == null;
    }

    public static <T> boolean  isNullOrEmpty(List<T> collection){
        return collection == null || collection.size() == 0;
    }

    public static <K,V> boolean isNullOrEmpty(HashMap<K,V> map){
        return map == null || map.isEmpty();
    }

    public static boolean isNullOrWhitespace(String str){
        return str == null || str.isBlank();
    }
}
