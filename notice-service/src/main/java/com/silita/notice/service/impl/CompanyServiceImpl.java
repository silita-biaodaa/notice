package com.silita.notice.service.impl;

import com.github.pagehelper.PageInfo;
import com.silita.notice.dao.RelQuaGradeMapper;
import com.silita.notice.dao.TbCompanyMapper;
import com.silita.notice.dao.TbNtRegexQuaMapper;
import com.silita.notice.service.CompanyService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private TbNtRegexQuaMapper tbNtRegexQuaMapper;
    @Autowired
    private TbCompanyMapper tbCompanyMapper;
    @Override
    public PageInfo queryCom(Map<String, Object> param) {
        List<Map<String,Object>> comListMap = new ArrayList<>();
        String noticeId = MapUtils.getString(param, "id");
        String quaRegex = tbNtRegexQuaMapper.queryNoticeId(noticeId);
        List<Map<String, Object>> comList = tbCompanyMapper.queryCom(quaRegex);
        PageInfo pageInfo = new PageInfo(comList);
        return pageInfo;
    }
}
