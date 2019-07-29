package com.silita.notice.dao;

import com.silita.notice.model.TbCommentInfo;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface TbCommentInfoMapper extends MyMapper<TbCommentInfo> {

    Integer queryCountComment(Map<String,Object> param);

    /**
     * 洗评论id
     * @param param
     */
    void updateRelatedId(Map<String,Object> param);

    void updateRelatedId2(Map<String,Object> param);

    /**
     * 获取地区    以下用于数据清洗
     * @return
     */
    List<String> querySource();

    List<String> queryReplySource();


    List<Map<String,Object>> queryRelatedId();


    List<Map<String,Object>> queryRelatedId2();

}