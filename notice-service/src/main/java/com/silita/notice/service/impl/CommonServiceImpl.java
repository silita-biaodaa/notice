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
     *
     * @return
     */
    public List<Map<String, Object>> getArea() {
        List<Map<String, Object>> areaList = new ArrayList<>();
        List<Map<String, Object>> list = sysAreaMapper.queryProvinceList();
        for (Map<String, Object> map : list) {
            Map<String, Object> area = new HashMap<>();
            String pkid = (String) map.get("pkid");

            List<Map<String, Object>> cityListT = sysAreaMapper.queryCityList(pkid);

            List<Map<String, Object>> cityListMap = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : cityListT) {
                Map<String, Object> cityMap = new HashMap<>();
                String areaName = (String) stringObjectMap.get("areaName");
                String areaCode = (String) stringObjectMap.get("areaCode");

                cityMap.put("name", areaName);
                cityMap.put("code", areaCode);
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
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> queryPbModes(Map<String, Object> param) {

        List<Map<String, Object>> pbModeList = new ArrayList<>();
        List<String> list = sysAreaMapper.queryCode();
        List<Map<String, Object>> list1 = null;
        for (String s : list) {
            Map<String, Object> map = new HashMap<>();
            param.put("pbModeType", s + "_pbmode");
            list1 = dicCommonMapper.queryPbModes(param);
            map.put(s, list1);
            pbModeList.add(map);
        }

        return pbModeList;

    }




    /**
     * 获取公告类型
     *
     * @return
     */
    public List<Map<String, Object>> type() {
        List<Map<String, Object>> typeList = new ArrayList<>();

        Map<String, Object> typeMap = new HashMap<>();
        //01:施工  02:监理  03:设计  04:勘察  05:采购  06:其他

        typeMap.put("name", "施工");
        typeMap.put("projectType", "01");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "监理");
        typeMap.put("projectType", "02");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "设计");
        typeMap.put("projectType", "03");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "勘察");
        typeMap.put("projectType", "04");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "采购");
        typeMap.put("projectType", "05");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "其他");
        typeMap.put("projectType", "06");
        typeList.add(typeMap);

        return typeList;

    }


    /**
     * 获取资质
     *
     * @param param
     */
    public List<Map<String, Object>> queryQua(Map<String, Object> param) {
        List<Map<String, Object>> list = dicQuaMapper.queryQuaOne(param);
        List<Map<String, Object>> oneQuaListtMap = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map<String, Object> oneQuaMap = new HashMap<>();
            String one = (String) map.get("id");
            param.put("zzIdOne", one);
            List<Map<String, Object>> list1 = dicQuaMapper.queryQuaTwo(param);
            List<Map<String, Object>> towQuaListtMap = new ArrayList<>();
            for (Map<String, Object> map2 : list1) {
                Map<String, Object> towQuaMap = new HashMap<>();
                String tow = (String) map2.get("id");
                param.put("zzIdTow", tow);
                List<Map<String, Object>> list2 = dicQuaMapper.queryQuaThree(param);
                List<Map<String, Object>> threeQuaListMap = new ArrayList<>();
                String towQualevel =(String) map2.get("quaCode");
                List<String> list8 = relQuaGradeMapper.queryRelQuaGrade(towQualevel);
                if (null == list8 || list8.size() <= 0) {
                    for (Map<String, Object> map3 : list2) {
                        Map<String, Object> threeQuaMap = new HashMap<>();
                        String three = (String) map3.get("id");
                        param.put("zzIdThree", three);
                        List<Map<String, Object>> list3 = dicQuaMapper.queryQuaFour(param);
                        String threeQualevel =(String) map3.get("quaCode");
                        List<String> list9 = relQuaGradeMapper.queryRelQuaGrade(threeQualevel);
                        if (null == list9 || list9.size() <= 0) {
                            List<Map<String, Object>> fourQuaListMap = new ArrayList<>();
                            for (Map<String, Object> map4 : list3) {
                                Map<String, Object> fourQuaMap = new HashMap<>();
                                String quaCode = (String) map4.get("quaCode");
                                List<String> list5 = relQuaGradeMapper.queryRelQuaGrade(quaCode);
                                List<Map<String, Object>> levelListMap = new ArrayList<>();
                                for (String s : list5) {
                                    List<Map<String, Object>> list6 = dicCommonMapper.queryQuaLevel(s);

                                    for (Map<String, Object> map5 : list6) {
                                        Map<String, Object> levelMap = new HashMap<>();

                                        levelMap.put("code", map5.get("quaCode"));
                                        levelMap.put("name", map5.get("quaName"));
                                        levelListMap.add(levelMap);

                                    }

                                }

                                fourQuaMap.put("code", map4.get("quaCode"));
                                fourQuaMap.put("name", map4.get("quaName"));
                                fourQuaMap.put("data", levelListMap);
                                fourQuaListMap.add(fourQuaMap);

                            }
                            threeQuaMap.put("code", map3.get("quaCode"));
                            threeQuaMap.put("name", map3.get("quaName"));
                            threeQuaMap.put("data", fourQuaListMap);
                            threeQuaListMap.add(threeQuaMap);
                        } else{
                            String quaCode = (String) map3.get("quaCode");
                            List<String> list5 = relQuaGradeMapper.queryRelQuaGrade(quaCode);
                            List<Map<String, Object>> levelListMap4 = new ArrayList<>();
                            for (String s : list5) {
                                List<Map<String, Object>> list6 = dicCommonMapper.queryQuaLevel(s);

                                for (Map<String, Object> map7 : list6) {
                                    Map<String, Object> levelMap3 = new HashMap<>();
                                    String b;
                                    levelMap3.put("code", map7.get("quaCode"));
                                    levelMap3.put("name", map7.get("quaName"));
                                    levelListMap4.add(levelMap3);


                                }

                            }

                            threeQuaMap.put("code", map2.get("quaCode"));
                            threeQuaMap.put("name", map2.get("quaName"));
                            threeQuaMap.put("data", levelListMap4);
                            towQuaListtMap.add(threeQuaMap);
                        }
                    }
                    towQuaMap.put("code", map2.get("quaCode"));
                    towQuaMap.put("name", map2.get("quaName"));
                    towQuaMap.put("data", threeQuaListMap);
                    towQuaListtMap.add(towQuaMap);
                }else{
                    String quaCode = (String) map2.get("quaCode");
                    List<String> list5 = relQuaGradeMapper.queryRelQuaGrade(quaCode);
                    List<Map<String, Object>> levelListMap = new ArrayList<>();
                    for (String s : list5) {
                        List<Map<String, Object>> list6 = dicCommonMapper.queryQuaLevel(s);

                        for (Map<String, Object> map6 : list6) {
                            Map<String, Object> levelMap2 = new HashMap<>();

                            levelMap2.put("code", map6.get("quaCode"));
                            levelMap2.put("name", map6.get("quaName"));
                            levelListMap.add(levelMap2);
                            String a;

                        }

                    }

                    towQuaMap.put("code", map2.get("quaCode"));
                    towQuaMap.put("name", map2.get("quaName"));
                    towQuaMap.put("data", levelListMap);
                    towQuaListtMap.add(towQuaMap);
                }


            }
            oneQuaMap.put("code", map.get("quaCode"));
            oneQuaMap.put("name", map.get("quaName"));
            oneQuaMap.put("data", towQuaListtMap);
            oneQuaListtMap.add(oneQuaMap);
        }


        return oneQuaListtMap;
    }
}
