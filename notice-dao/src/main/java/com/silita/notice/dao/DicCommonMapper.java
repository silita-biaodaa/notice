package com.silita.notice.dao;

import com.silita.notice.model.DicCommon;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DicCommonMapper extends MyMapper<DicCommon> {

    /**
     * 获取评标办法
     * @param param
     * @return
     */
    List<Map<String,Object>> queryPbModes(Map<String,Object> param);


}