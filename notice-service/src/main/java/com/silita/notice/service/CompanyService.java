package com.silita.notice.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    /**
     * 查询符合该公告资质的所有企业
     * @param param
     * @return
     */
    PageInfo queryCom(Map<String,Object> param);

    /**
     * 获取符合该公告资质的企业数量
     * @param param
     * @return
     */
    Integer relCompanySize(Map<String,Object> param);

    /**
     * 获取符合该公告资质的企业
     * @param param
     * @return
     */
    PageInfo queryQualCom(Map<String,Object> param);
}
