package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.PhoneCommon;
import com.silita.notice.common.RangeCommon;
import com.silita.notice.common.RegionCommon;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.*;
import com.silita.notice.service.CompanyService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ColleCompanyNewMapper colleCompanyNewMapper;
    @Autowired
    private RelQuaGradeMapper relQuaGradeMapper;
    @Autowired
    private DicCommonMapper dicCommonMapper;

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
        if (list.size() > 0 && null != list) {
            param.put("list", list);
            List<Map<String, Object>> list1 = relQuaGradeMapper.queryQuaCodeGradeCode(param);
            List<Map<String, Object>> rangeListMap = new ArrayList<>();
            for (Map<String, Object> map : list1) {
                Map<String, Object> rankMap = new HashMap<>();
                String quaCode = MapUtils.getString(map, "quaCode");
                String gradeCode = MapUtils.getString(map, "gradeCode");
                String rankName = dicCommonMapper.queryRank(gradeCode);
                String rangeNameCode="";
                if(StringUtils.isNotEmpty(rankName)) {
                    rangeNameCode = (String) RangeCommon.rangeCode.get(rankName);
                    String[] split = rangeNameCode.split(",");
                    for (int i = 0; i < split.length; i++) {
                        Map<String, Object> relMap = new HashMap<>();
                        param.put("quaCode", quaCode);
                        param.put("gradeCode", split[i]);
                        String relId = relQuaGradeMapper.queryId(param);
                        relMap.put("id", relId);
                        rangeListMap.add(relMap);
                    }
                }
            }
            param.put("rangeListMap", rangeListMap);
            //获取符合数量
            Integer relCompanySize = tbCompanyMapper.queryRelCompanySize(param);
            count = relCompanySize;
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
        //根据
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);
        List<Map<String, Object>> rangeListMap = new ArrayList<>();
        if (list != null && list.size() > 0) {
            param.put("list", list);
            List<Map<String, Object>> list1 = relQuaGradeMapper.queryQuaCodeGradeCode(param);
            if (list1 != null && list1.size() > 0) {
                for (Map<String, Object> map : list1) {
                    Map<String, Object> rankMap = new HashMap<>();
                    String quaCode = MapUtils.getString(map, "quaCode");
                    String gradeCode = MapUtils.getString(map, "gradeCode");
                    String rankName = dicCommonMapper.queryRank(gradeCode);
                    String rangeNameCode = (String) RangeCommon.rangeCode.get(rankName);
                    String[] split = rangeNameCode.split(",");
                    for (int i = 0; i < split.length; i++) {
                        Map<String, Object> relMap = new HashMap<>();
                        param.put("quaCode", quaCode);
                        param.put("gradeCode", split[i]);
                        String relId = relQuaGradeMapper.queryId(param);
                        relMap.put("id", relId);
                        rangeListMap.add(relMap);

                    }

                }
            }
        }
        param.put("rangeListMap", rangeListMap);
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> listMap = tbCompanyMapper.queryQualCom(param);
        if (listMap != null && listMap.size() >0) {
            param.put("userId", VisitInfoHolder.getUserId());
            Integer isVip = MapUtils.getInteger(param, "isVip");
            for (Map<String, Object> map : listMap) {
                String comName = (String) map.get("comName");
                param.put("comName", comName);
                String phone = tbCompanyMapper.queryQualComPhone(param);
                if (StringUtils.isNotEmpty(phone)) {
                    map.put("phone", PhoneCommon.phones(phone, isVip));
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
        }
        PageInfo pageInfo = new PageInfo(listMap);
        return pageInfo;
    }

}
