package com.silita.notice.service.impl;

import com.silita.notice.dao.TbNtMianHunanMapper;
import com.silita.notice.model.TbNtMianHunan;
import com.silita.notice.service.TbNtMianHunanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("notice")
public class TbNtMianHunanServiceimpl implements TbNtMianHunanService {
    @Autowired
    private TbNtMianHunanMapper tbNtMianHunanMapper;

    @Override
    public List<Map<String, Object>> queryBids(TbNtMianHunan nociteMian) {
        return tbNtMianHunanMapper.queryBids(nociteMian);
    }

    @Override
    public List<String> queryCompanyName(String CompanyName) {
        return tbNtMianHunanMapper.queryCompanyName(CompanyName);
    }


}
