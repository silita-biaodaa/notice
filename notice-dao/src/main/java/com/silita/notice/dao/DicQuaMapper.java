package com.silita.notice.dao;

import com.silita.notice.dao.DicQuaMapper;
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

     List<Map<String,Object>> queryQuaOne(Map<String,Object> param);

    /**
     * 获取资质级别
     */
    List<Map<String,Object>> queryQuaTwo(Map<String,Object> param);



}