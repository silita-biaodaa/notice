package com.silita.notice.service;

import com.silita.notice.model.TbNtMianHunan;

import java.util.List;
import java.util.Map;

public interface TbNtMianHunanService{


    /**
     * 查询中标公告
     * @param nociteMian
     * @return
     */
    List<Map<String,Object>> queryBids(TbNtMianHunan nociteMian);




    /**
     * 通过公司名称模糊查询公司中标记录
     * 全国
     */

    //List<Map<String,Object>> queryCompanyName(TbNtMianHunan nociteMian, String CompanyName);
    List<String> queryCompanyName(String CompanyName);
}