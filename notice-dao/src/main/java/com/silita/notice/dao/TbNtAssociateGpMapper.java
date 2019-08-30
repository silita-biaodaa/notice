package com.silita.notice.dao;


import java.util.List;
import java.util.Map;

/**
 * 公告的相关公告 tb_nt_associate_gp
 */
public interface TbNtAssociateGpMapper {

    /**
     * 查询公告的相关公告
     * @param param
     * @return
     */
    List<Map<String,Object>> queryNoticeCorrelationList(Map<String,Object> param);


}