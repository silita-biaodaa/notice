package com.silita.notice.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

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


    public static String getUserId(HttpServletRequest request) {
        String userId = null;
        String xToken = request.getHeader("X-TOKEN");
        if (StringUtils.isEmpty(xToken)) {
            return null;
        }
        String[] token = xToken.split("\\.");
        String[] sArray = xToken.split(Constant.TOKEN_SPLIT);
        String paramJson = sArray[1];
        try {
            paramJson = new String(Base64.getDecoder().decode(paramJson), Constant.STR_ENCODING);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(paramJson);
            userId = jsonObject.getString("pkid");
            return userId;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setUserId(String userId) {
        VisitInfoHolder.userId.set(userId);
    }
}
