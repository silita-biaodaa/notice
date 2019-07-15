package com.silita.notice.dao;

import com.silita.notice.model.RelQuaGrade;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RelQuaGradeMapper extends MyMapper<RelQuaGrade> {

    /**
     * 通过子级code找到对应的等级code
     * @return
     */
    List<Map<String,Object>> queryRelQuaGrade(String quaCode);
}