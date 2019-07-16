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
}
