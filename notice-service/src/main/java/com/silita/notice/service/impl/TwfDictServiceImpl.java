package com.silita.notice.service.impl;

import com.silita.notice.dao.TwfDictMapper;
import com.silita.notice.model.TwfDict;
import com.silita.notice.service.TwfDictService;
import com.silita.notice.utils.PinYinUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;

@Service
public class TwfDictServiceImpl implements TwfDictService {
    @Autowired
    private TwfDictMapper twfDictMapper;

    @Override
    public void addData(Map<String,Object> param) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        String s = MapUtils.getString(param, "s");
        PinYinUtil pinYinUtil = new PinYinUtil();
        String[] split = s.split("\\,");
        List<String> list1 = Arrays.asList(split);


        for (String s2 : list1) {
            String pinyin = "filing_pfm"+"_"+pinYinUtil.cn2py(s2)+"_"+System.currentTimeMillis();
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("pinyin",pinyin);
            maps.put("name",s2);

            listmap.add(maps);
        }
        param.put("list",listmap);
        twfDictMapper.addData(param);
    }


}
