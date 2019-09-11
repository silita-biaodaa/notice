package com.silita.notice.dao;

import java.util.List;
import java.util.Map;

/**
 * 订阅 tb_user_subscribe
 */
public interface TbUserSubscribeMapper {

    /**
     * 查询需要推送的用户数量
     *
     * @return
     */
    int queryUserSubscibe();

    /**
     * 查询需要推送的用户和条件
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryListUserSubscibe(Map<String, Object> param);
}