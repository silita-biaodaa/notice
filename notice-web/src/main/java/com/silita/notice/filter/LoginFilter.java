package com.silita.notice.filter;

import com.alibaba.fastjson.JSONObject;
import com.silita.notice.common.Constant;
import com.silita.notice.common.ResponseCode;
import com.silita.notice.common.SecurityCheck;
import com.silita.notice.common.VisitInfoHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhushuai on 2019/5/31.
 */
public class LoginFilter implements Filter {

    private String tokenVersion;
    private String filterUrl;
    private String blacklist;


    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init......filter");
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        tokenVersion = properties.get("token.version").toString();
        if (null != properties.get("FILTER_URL")) {
            filterUrl = properties.get("FILTER_URL").toString();
        }
        if (null != properties.get("blacklist")) {
            blacklist = properties.get("blacklist").toString();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map resMap = new HashMap();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String ipAddr = request.getHeader("X-real-ip");
        String xToken = request.getHeader("X-TOKEN");
        String requestUrl = request.getRequestURI();
        logger.info("-----requesturi:" + requestUrl + "-------------------");
        try {
            String phone = null;
            String userId = null;
            boolean tokenValid = false;
            if (StringUtils.isNotEmpty(xToken)) {
                String[] token = xToken.split("\\.");
                if (token.length == 3) {
                    if (verifyTokenVersion(xToken)) {
                        String[] sArray = xToken.split(Constant.TOKEN_SPLIT);
                        String paramJson = sArray[1];
                        String sign = sArray[2];
                        paramJson = Base64Decode(paramJson);
                        Map<String, String> paramMap = parseJsonString(paramJson);
                        VisitInfoHolder.setPermissions(paramMap.get("permissions"));
                        VisitInfoHolder.setRoleCode(paramMap.get("roleCode"));
                        userId = paramMap.get("pkid");
                        phone = paramMap.get("phoneNo");
                        //验证签名
                        tokenValid = SecurityCheck.checkSigner(paramMap, sign);
                    } else {
                        resMap.put("code", ResponseCode.WARN_CODE_503);
                        resMap.put("msg", ResponseCode.WARN_MSG_503);
                        printInfo(response, resMap);
                    }
                } else {
                    logger.warn("非法token![token:" + token + "][ip:" + ipAddr + "]");
                    resMap.put("code", ResponseCode.WARN_CODE_504);
                    resMap.put("msg", ResponseCode.WARN_MSG_504);
                    printInfo(response, resMap);
                }
                //是否疑似爬虫
                if (null != blacklist && null != phone && blacklist.contains(phone)) {
                    resMap.put("code", ResponseCode.WARN_CODE_502);
                    resMap.put("msg", ResponseCode.WARN_MSG_502);
                    printInfo(response, resMap);
                    return;
                }
            } else {
                //绿色通道检查
                boolean greenWay = greenWayVerify(requestUrl, filterUrl, xToken);
                if (greenWay) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
                //非法token
                logger.warn("非法token![token:" + xToken + "][ip:" + ipAddr + "]");
                resMap.put("code", ResponseCode.WARN_CODE_504);
                resMap.put("msg", ResponseCode.WARN_MSG_504);
                printInfo(response, resMap);
                return;
            }

            //设置userid
            if (StringUtils.isNotEmpty(userId)) {
                VisitInfoHolder.setUserId(userId);
            }
            if (tokenValid) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                resMap.put("code", ResponseCode.WARN_CODE_504);
                resMap.put("msg", ResponseCode.WARN_MSG_504);
                printInfo(response, resMap);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public void destroy() {

    }

    private static String Base64Decode(String s) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(s), Constant.STR_ENCODING);
    }

    /**
     * 验证token版本是否和当前server版本一致
     *
     * @param xToken
     * @return
     */
    private boolean verifyTokenVersion(String xToken) {
        logger.info("-------------token.version:" + tokenVersion + "----------------------");
        String[] tokens = xToken.split(Constant.TOKEN_SPLIT);
        try {
            String version = Base64Decode(tokens[0]);
            logger.info("-------------version:" + version + "----------------------");
            if (version.equals(tokenVersion)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("证token版本是否和当前server版本失败", e);
        }
        return false;
    }

    /**
     * 从token中解析用户信息
     *
     * @param paramJson
     * @return
     */
    private static Map<String, String> parseJsonString(String paramJson) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(paramJson);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("loginName", jsonObject.getString("loginName"));
        paramMap.put("userName", jsonObject.getString("userName"));
        paramMap.put("pkid", jsonObject.getString("pkid"));
        paramMap.put("channel", jsonObject.getString("channel"));
        paramMap.put("phoneNo", jsonObject.getString("phoneNo"));
        paramMap.put("loginTime", jsonObject.getString("loginTime"));
        paramMap.put("tokenVersion", jsonObject.getString("tokenVersion"));
        paramMap.put("roleCode", jsonObject.getString("roleCode"));
        paramMap.put("permissions", jsonObject.getString("permissions"));
        return paramMap;
    }


    /**
     * 绿色通道检查
     *
     * @param requestUri
     * @param filterUrl
     * @param xToken
     * @return
     */
    private boolean greenWayVerify(String requestUri, String filterUrl, String xToken) {
        boolean greenWay = false;
        if (requestUri.equals("/") || "biaodaaTestToken".equals(xToken)) {
            greenWay = true;
        } else {
            if (filterUrl != null) {
                String[] urls = filterUrl.split(",");
                for (String url : urls) {
                    if (requestUri.indexOf(url.trim()) > -1) {
                        greenWay = true;
                        break;
                    }
                }
            }
        }
        return greenWay;
    }

    /**
     * 输出
     *
     * @param response
     * @param map
     * @throws IOException
     */
    private void printInfo(HttpServletResponse response, Map map) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(JSONObject.toJSONString(map));
        out.close();
    }

    public static void main(String[] args) {
        String requerUrl = "/newnocite/zhaobiao/list";
        System.out.println(requerUrl.indexOf("/newnocite") > -1);
    }
}
