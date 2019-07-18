package com.silita.notice.dao;


import com.silita.notice.model.Snatchurl;
import com.silita.notice.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SnatchurlMapper extends MyMapper<Snatchurl> {

    List<Map<String,Object>> queryNtid();

    List<String> queryTitle(Map<String,Object> param);
}