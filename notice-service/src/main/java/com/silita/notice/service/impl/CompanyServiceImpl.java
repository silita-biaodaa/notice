package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.*;
import com.silita.notice.service.CompanyService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private TbNtRegexQuaMapper tbNtRegexQuaMapper;
    @Autowired
    private TbCompanyMapper tbCompanyMapper;

    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private ColleCompanyNewMapper colleCompanyNewMapper;


    @Override
    public PageInfo queryCom(Map<String, Object> param) {
        List<Map<String, Object>> comListMap = new ArrayList<>();
        String noticeId = MapUtils.getString(param, "id");
        String quaRegex = tbNtRegexQuaMapper.queryNoticeId(noticeId);
        param.put("quaRegex", quaRegex);
        List<Map<String, Object>> comList = tbCompanyMapper.queryQualCom(param);
        PageInfo pageInfo = new PageInfo(comList);
        return pageInfo;
    }

    /**
     * 获取符合该公告资质企业的数量
     *
     * @param param
     * @return
     */
    @Override
    public Integer relCompanySize(Map<String, Object> param) {
        Integer count = 0;
        //获取省份的name
        String name = sysAreaMapper.queryName(param);
        param.put("regisAddress", name);
        //获取资质关系表达式的列quaRegex
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);
        if(list.size() >= 0 || null != list){
            for (String s1 : list) {
                param.put("quaRegex", s1);
                //获取符合数量
                Integer relCompanySize = tbCompanyMapper.queryRelCompanySize(param);
                count = count + relCompanySize;
            }
        }


        return count;
    }

    /**
     * 获取符合该公告资质的企业
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo queryQualCom(Map<String, Object> param) {
        String name = sysAreaMapper.queryName(param);
        param.put("regisAddress", name);
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);

        param.put("list", list);
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);


        List<Map<String, Object>> listMap = tbCompanyMapper.queryQualCom(param);
        param.put("userId", VisitInfoHolder.getUserId());


        for (Map<String, Object> stringObjectMap : listMap) {
            param.put("comId",stringObjectMap.get("comId"));
            List<String> list1 = colleCompanyNewMapper.queryYesOrNo(param);
            if (list1 != null){
                stringObjectMap.put("collected","YES") ;
            }else{
                stringObjectMap.put("collected","NO");
            }

        }



        PageInfo pageInfo = new PageInfo(listMap);
        return pageInfo;
    }


}
