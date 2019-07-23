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
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQualCom(Map<String,Object> param);


    /**
     * 获取符合该资质的企业数量
     * @param param
     * @return
     */
    Integer queryRelCompanySize(Map<String,Object> param);

    /*
     * 根据企业名称获取企业资质
     */
    String queryComNameQual(Map<String,Object> param);




}