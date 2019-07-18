package com.silita.notice.service;

import java.util.List;
import java.util.Map;

public interface CommonService {



    /**
     * 获取省市
     * @return
     */
    List<Map<String,Object>> getArea();

    /**
     * 获取评标办法
     * @param param
     * @return
     */
    List<Map<String,Object>> queryPbModes(Map<String,Object> param);



    /**
     * 获取公告类型
     * @return
     */
    List<Map<String,Object>> type();

    /**
     * 获取资质
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQua(Map<String,Object> param);


    //List<Map<String, Object>> querytestss(Map<String,Object> param);






}
