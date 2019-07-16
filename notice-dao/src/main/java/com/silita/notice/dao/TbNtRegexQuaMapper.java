package com.silita.notice.dao;

import com.silita.notice.model.TbNtRegexQua;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbNtRegexQuaMapper extends MyMapper<TbNtRegexQua> {

    /**
     * 根据公告id获取关系表达式的qua_regex
      * @param noticeId
     * @return
     */
    String queryNoticeId(String noticeId);
}