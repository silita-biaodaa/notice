package com.silita.notice.dao;

import java.util.List;

/**
 * Created by zhushuai on 2019/12/30.
 */
public interface TbBackListMapper {

    /**
     * 查询所有黑名单
     *
     * @return
     */
    List<String> queryBackList();

    /**
     * 查看ip是否爬虫
     *
     * @param ip
     * @return
     */
    int queryIpBlack(String ip);
}
