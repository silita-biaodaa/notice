package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.PhoneCommon;
import com.silita.notice.common.RegionCommon;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.ColleCompanyNewMapper;
import com.silita.notice.dao.SysAreaMapper;
import com.silita.notice.dao.TbCompanyMapper;
import com.silita.notice.dao.TbNtRegexQuaMapper;
import com.silita.notice.service.CompanyService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String source = MapUtils.getString(param, "source");
        //获取省份的name
        param.put("regisAddress", RegionCommon.regionSource.get(source));
        //获取资质关系表达式的列quaRegex
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);
        if (list.size() >= 0 || null != list) {
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

        String source = MapUtils.getString(param, "source");
        //获取省份名称
        param.put("regisAddress", RegionCommon.regionSource.get(source));
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);
        param.put("list", list);
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> listMap = tbCompanyMapper.queryQualCom(param);
        param.put("userId", VisitInfoHolder.getUserId());
        Integer isVip = MapUtils.getInteger(param, "isVip");
        for (Map<String, Object> map : listMap) {
            String comName =(String) map.get("comName");
            param.put("comName",comName);
            String phone = tbCompanyMapper.queryQualComPhone(param);
            if (StringUtils.isNotEmpty(phone)) {
                map.put("phone",PhoneCommon.phones(phone,isVip));
            }
            param.put("comId", map.get("comId"));
            Integer collectCount = colleCompanyNewMapper.queryTrueFalse(param);
            //用户是否关注企业
            if (collectCount > 0) {
                map.put("collected", true);
            } else {
                map.put("collected", false);
            }
        }
        PageInfo pageInfo = new PageInfo(listMap);
        return pageInfo;
    }


}
