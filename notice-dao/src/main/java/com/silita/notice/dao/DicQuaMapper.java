package com.silita.notice.dao;

import com.silita.notice.model.DicQua;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DicQuaMapper extends MyMapper<DicQua> {

    /**
     * 获取一级资质
     */

    List<Map<String, Object>> queryQuaOne(Map<String, Object> param);

    /**
     * 获取资质级别
     */
    List<Map<String, Object>> queryQuaTwo(Map<String, Object> param);

    /**
     * 查询非标准名称的资质
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQual(Map<String,Object> param);

    /**
     * 查询所有的标准名称资质
     * @return
     */
    List<Map<String,Object>> queryBenchQual(String bizType);
}