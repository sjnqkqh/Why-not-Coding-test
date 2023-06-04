package com.why_not_cote.util;

public class CustomObjectUtils {

    public static boolean isNotEmpty(Object obj){
        return !org.springframework.util.ObjectUtils.isEmpty(obj);
    }

    public static boolean isEmpty(Object obj){
        return org.springframework.util.ObjectUtils.isEmpty(obj);
    }

    public static boolean isNotNull(Object obj){
        return obj != null;
    }

    public static boolean isNull(Object obj){
        return obj == null;
    }

}
