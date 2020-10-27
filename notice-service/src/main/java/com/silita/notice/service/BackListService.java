package com.silita.notice.service;

import java.util.List;

public interface BackListService {

    /**
     * 获取黑名单
     *
     * @return
     */
    List<String> getBackList();

    /**
     * 查询ip是否爬虫
     *
     * @param ip
     * @return
     */
    int getCountBlack(String ip);
}