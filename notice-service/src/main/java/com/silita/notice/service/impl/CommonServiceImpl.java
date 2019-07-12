package com.silita.notice.service.impl;

import com.silita.notice.dao.DicCommonMapper;
import com.silita.notice.dao.DicQuaMapper;
import com.silita.notice.dao.RelQuaGradeMapper;
import com.silita.notice.dao.SysAreaMapper;
import com.silita.notice.service.CommonService;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private DicCommonMapper dicCommonMapper;

    @Autowired
    private DicQuaMapper dicQuaMapper;

    @Autowired
    private RelQuaGradeMapper relQuaGradeMapper;



    /**
     * 获取省市
     * @return
     */
    public List<Map<String,Object>> getArea(){
        List<Map<String, Object>> areaList = new ArrayList<>();
        List<Map<String, Object>> list = sysAreaMapper.queryProvinceList();
        for (Map<String, Object> map : list) {
            Map<String, Object> area = new HashMap<>();
            String pkid = (String)map.get("pkid");

            List<Map<String, Object>> cityListT = sysAreaMapper.queryCityList(pkid);

            List<Map<String,Object>> cityListMap = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : cityListT) {
                Map<String,Object> cityMap = new HashMap<>();
                 String areaName = (String) stringObjectMap.get("areaName");
                String areaCode = (String) stringObjectMap.get("areaCode");

                 cityMap.put("name",areaName);
                 cityMap.put("code",areaCode);
                cityListMap.add(cityMap);

            }



            area.put("name", map.get("areaName"));
            area.put("code", map.get("areaCode"));
            area.put("data", cityListMap);
            areaList.add(area);
        }
        return areaList;
    }

    /**
     * 查询对应省的评标办法
     * @param param
     * @return
     */
    @Override
    public List<Map<String,Object>> queryPbModes(Map<String, Object> param) {

        List<Map<String,Object>> pbModeList = new ArrayList<>();
        List<String> list = sysAreaMapper.queryCode();
        List<Map<String, Object>> list1=null;
        for (String s : list) {
            Map<String,Object> map = new HashMap<>();
            param.put("pbModeType", s+"_pbmode");
            list1 = dicCommonMapper.queryPbModes(param);
            map.put(s,list1);
            pbModeList.add(map);
        }

        return pbModeList;

    }

    /**
     * 获取资质
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> queryCompanyQual(Map<String, Object> param) {
        List<Map<String,Object>> qualList = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> list = dicQuaMapper.queryQua1(param);
        for (Map<String, Object> map : list) {
            Map<String, Object> qual = new HashMap<>();
            String id = (String)map.get("id");
            param.put("zzId",id);
            List<Map<String, Object>> qualListT = dicQuaMapper.queryQua2(param);
            List<Map<String,Object>> cityListMap = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : qualListT) {
                Map<String,Object> cityMap = new HashMap<>();
                String quaName = (String) stringObjectMap.get("quaName");
                String quaCode = (String) stringObjectMap.get("quaCode");

                List<String> list1 = relQuaGradeMapper.queryRelQuaGrade(quaCode);
                List<Map<String,Object>> levelList = new ArrayList<>();
                for (String s : list1) {
                    List<Map<String, Object>> list2 = dicCommonMapper.queryQuaLevel(s);

                    for (Map<String, Object> objectMap : list2) {
                        Map<String,Object> levelMap = new HashMap<>();
                        String code = (String) objectMap.get("code");
                        String name = (String) objectMap.get("name");
                        levelMap.put("code",code);
                        levelMap.put("name",name);
                        levelList.add(levelMap);

                        //cityMap.put("data",levelList);
                    }

                }

                cityMap.put("name",quaName);
                cityMap.put("code",quaCode);
                cityMap.put("data",levelList);
                cityListMap.add(cityMap);


            }
            qual.put("name", map.get("quaName"));
            qual.put("code", map.get("quaCode"));
            qual.put("data", cityListMap);
            qualList.add(qual);
        }

        return qualList;
    }


    /**
     * 获取公告类型
     * @return
     */
    public List<Map<String,Object>> type(){
        List<Map<String,Object>> typeList = new ArrayList<>();

        Map<String,Object> typeMap = new HashMap<>();
        //01:施工  02:监理  03:设计  04:勘察  05:采购  06:其他

        typeMap.put("name","施工");
        typeMap.put("projectType","01");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name","监理");
        typeMap.put("projectType","02");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name","设计");
        typeMap.put("projectType","03");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name","勘察");
        typeMap.put("projectType","04");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name","采购");
        typeMap.put("projectType","05");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name","其他");
        typeMap.put("projectType","06");
        typeList.add(typeMap);

        return typeList;

    }
}
