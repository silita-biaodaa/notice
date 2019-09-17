package com.silita.notice.utils;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description: HttpUtil
 *
 * @author zhushuai
 * @version 1.0
 * @since JDK 1.8
 */
public class HttpUtils {

    final static String ProxyUser = "H42I0796HK140EUD";
    final static String ProxyPass = "169AE1671CB4912F";

    // 代理服务器
    final static String ProxyHost = "http-dyn.abuyun.com";
    final static Integer ProxyPort = 9020;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HttpUtils.class);

    public static String connectURL(String address, String jsonstr, String requestMethod) {
        String result = "";
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000 * 60 * 5);
            conn.setReadTimeout(1000 * 60 * 5);
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            OutputStream out = conn.getOutputStream();
            out.write(jsonstr.getBytes("UTF-8"));
            out.flush();
            out.close();

            String resCode = new Integer(conn.getResponseCode()).toString();
            logger.info("http请求响应码:" + resCode);
            InputStream input = resCode.startsWith("2") ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            result = reader.readLine();
            logger.info("http请求返回数据:" + result);
        } catch (Exception e) {
            logger.error("http提交请求异常,", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }
}
