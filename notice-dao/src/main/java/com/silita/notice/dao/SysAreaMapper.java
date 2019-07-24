package com.silita.notice.dao;

import com.silita.notice.model.SysArea;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface SysAreaMapper extends MyMapper<SysArea> {

    /**
     * 获取省份
     * @return
     */
    List<Map<String,Object>> queryProvinceList();



    /**
     * 获取城市
     * @param pkid
     * @return
     */
    List<Map<String,Object>> queryCityList(String pkid);


    /**
     * 获取省的code
     * @return
     */
    List<String> queryCode();




}