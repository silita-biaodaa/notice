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

    List<String> querySource();

}