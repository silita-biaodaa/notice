package com.silita.notice.dao;


import com.silita.notice.model.TbCompany;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TbCompanyMapper extends MyMapper<TbCompany> {

    /**
     *
     * 查询附和该公告资质的建筑企业
     * @param id
     * @return
     */
    List<Map<String,Object>> queryCom(String id);

    /*
     * 根据企业名称获取企业资质
     */
    List<Map<String,Object>> queryComQua(Map<String,Object> param);

}