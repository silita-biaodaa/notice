package com.silita.notice.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangxiahui on 17/7/26.
 */
public class VisitInfoHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitInfoHolder.class);

    protected static final ThreadLocal<String> userId = new ThreadLocal<>();

    protected static final ThreadLocal<String> role_code = new ThreadLocal<>();

    protected static final ThreadLocal<String> permissions = new ThreadLocal<>();

    public static void setRoleCode(String roleCode){
        VisitInfoHolder.role_code.set(roleCode);
    }

    public static void setPermissions(String permissions){
        VisitInfoHolder.permissions.set(permissions);
    }

    public static String getRoleCode(){
        return VisitInfoHolder.role_code.get();
    }

    public static String getPermissions(){
        return VisitInfoHolder.permissions.get();
    }


    public static String getUserId() {
        return VisitInfoHolder.userId.get();
    }

    public static void setUserId(String userId) {
        VisitInfoHolder.userId.set(userId);
    }
}
