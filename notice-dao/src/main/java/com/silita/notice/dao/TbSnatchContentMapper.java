package com.silita.notice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TbSnatchContentMapper {

    /**
     * 查询总数
     *
     * @return
     */
    int selectCount();

    /**
     * 查询id
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> selectListId(Map<String, Object> param);

    /**
     * 将内容填充
     */
    void updateContent(@Param("pkid") int pkid, @Param("content") String content);

    /**
     * 根据id查询内容
     *
     * @param snatchId
     * @return
     */
    String selectContent(String snatchId);
}