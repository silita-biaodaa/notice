package com.silita.notice.common;

import com.silita.notice.utils.SignConvertUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by zhangxiahui on 17/7/26.
 */
public class SecurityCheck {

    private static final String secret = "BiAoDaA_2017#04*13tEsT";

    private static final Logger logger = Logger.getLogger(SecurityCheck.class);

    public static String getCookieValue(HttpServletRequest request, String name) {
        String value = null;
        if(request != null && request.getCookies() != null) {
            for(Cookie ck : request.getCookies()) {
                if(ck.getName().contains(name)) {
                    value = ck.getValue();
                }
            }
        }
        return value;
    }

    public static String getHeaderValue(HttpServletRequest request, String name) {
        String value = null;
        if(request != null) {
            value = request.getHeader(name);
        }
        return value;
    }

    public static boolean checkSigner(Map<String, String> parameters, String contentSign) {
        boolean alongBoo = false;
        String sign = "";
        if(parameters != null && contentSign != null) {
            try {
                sign = SignConvertUtil.generateMD5Sign(secret, parameters);
            } catch(NoSuchAlgorithmException e) {
                logger.error(e.getMessage(), e);
            } catch(UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
            if(contentSign.equals(sign)) {
                //鉴权成功
                alongBoo = true;
            }
        }
        return alongBoo;
    }
}
