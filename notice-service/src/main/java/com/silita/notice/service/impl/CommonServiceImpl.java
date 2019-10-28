package com.silita.notice.service.impl;

import com.silita.notice.common.MapCommon;
import com.silita.notice.dao.DicCommonMapper;
import com.silita.notice.dao.DicQuaMapper;
import com.silita.notice.dao.RelQuaGradeMapper;
import com.silita.notice.dao.SysAreaMapper;
import com.silita.notice.service.CommonService;
import com.silita.notice.utils.ObjectUtils;
import com.silita.notice.utils.RedisShardedPoolUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
                area.put("areaShortName", stringObjectMap.get("areaShortName"));
                cityListMap.add(cityMap);
            }
            area.put("name", map.get("areaName"));
            area.put("code", map.get("areaCode"));
            area.put("areaShortName", map.get("areaShortName"));
            area.put("shortName", map.get("shortName"));
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
        for (String s : list) {
            Map<String, Object> map = new HashMap<>();
            param.put("pbModeType", s + "_pbmode");
            List<Map<String, Object>> list1 = dicCommonMapper.queryPbModes(param);
            map.put("provice", s);
            map.put("list", list1);
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
        return MapCommon.getNoticeType();//获取公告类型
    }

    /**
     * 获取资质
     *
     * @param param
     */
    public List<Map<String, Object>> queryQua(Map<String, Object> param) {
        List<Map<String, Object>> list = dicQuaMapper.queryQuaOne(param);
        List<Map<String, Object>> oneQuaListtMap = new ArrayList<>();
        //遍历资质一级
        for (Map<String, Object> map : list) {
            //把一级资质放入oneQuaMap中
            Map<String, Object> oneQuaMap = new HashMap<>();
            String one = (String) map.get("id");
            param.put("zzIdOne", one);
            param.put("noticeLevel", "2");
            List<Map<String, Object>> list1 = dicQuaMapper.queryQuaTwo(param);
            List<Map<String, Object>> towQuaListtMap = new ArrayList<>();
            for (Map<String, Object> map2 : list1) {
                Map<String, Object> towQuaMap = new HashMap<>();
                String tow = (String) map2.get("id");
                param.put("zzIdOne", tow);
                String towQualevel = (String) map2.get("quaCode");
                List<Map<String, Object>> list8 = relQuaGradeMapper.queryRelQuaGrade(towQualevel);
                List<Map<String, Object>> levelThreeListMap = new ArrayList<>();
                if (null == list8 || list8.size() <= 0) {
                    param.put("noticeLevel", "3");
                    List<Map<String, Object>> list2 = dicQuaMapper.queryQuaTwo(param);
                    for (Map<String, Object> map3 : list2) {
                        Map<String, Object> threeQuaMap = new HashMap<>();
                        String three = (String) map3.get("id");
                        param.put("zzIdOne", three);
                        param.put("noticeLevel", "4");
                        String threeQualevel = (String) map3.get("quaCode");
                        List<Map<String, Object>> list9 = relQuaGradeMapper.queryRelQuaGrade(threeQualevel);
                        List<Map<String, Object>> levelFourListMap = new ArrayList<>();
                        if (null == list9 || list9.size() <= 0) {
                            List<Map<String, Object>> list3 = dicQuaMapper.queryQuaTwo(param);
                            for (Map<String, Object> map4 : list3) {
                                Map<String, Object> fourQuaMap = new HashMap<>();
                                String quaCode = (String) map4.get("quaCode");
                                List<Map<String, Object>> list5 = relQuaGradeMapper.queryRelQuaGrade(quaCode);
                                List<Map<String, Object>> levelFiveListMap = new ArrayList<>();
                                for (Map<String, Object> map5 : list5) {
                                    Map<String, Object> levelMap = new HashMap<>();
                                    getQuaMap("quaName", map5, levelMap, null, levelFiveListMap);
                                }
                                String benchName = (String) map4.get("benchName");
                                if (StringUtils.isNotEmpty(benchName)) {
                                    getQuaMap("benchName", map4, fourQuaMap, levelFiveListMap, towQuaListtMap);
                                }
                            }
                        } else {
                            for (Map<String, Object> map7 : list9) {
                                Map<String, Object> levelMap3 = new HashMap<>();
                                getQuaMap("quaName", map7, levelMap3, null, levelFourListMap);
                            }
                        }
                        String benchName = (String) map3.get("benchName");
                        if (StringUtils.isNotEmpty(benchName)) {
                            getQuaMap("benchName", map3, threeQuaMap, levelFourListMap, towQuaListtMap);
                        }
                    }
                } else {
                    for (Map<String, Object> map6 : list8) {
                        Map<String, Object> levelMap2 = new HashMap<>();
                        getQuaMap("quaName", map6, levelMap2, null, levelThreeListMap);
                    }
                }
                String benchName = (String) map2.get("benchName");
                if (StringUtils.isNotEmpty(benchName)) {
                    getQuaMap("benchName", map2, towQuaMap, levelThreeListMap, towQuaListtMap);
                }
            }
            getQuaMap("quaName", map, oneQuaMap, towQuaListtMap, oneQuaListtMap);
        }
        return oneQuaListtMap;
    }

    public void getQuaMap(String name, Map<String, Object> map, Map<String, Object> quaMap,
                          List<Map<String, Object>> getListMap, List<Map<String, Object>> quaListMap) {
        quaMap.put("code", map.get("quaCode"));
        quaMap.put("name", map.get(name));
        //getListMap 为空时 需要显示date时 可以把 getListMap.size() > 0 去掉
        if (null != getListMap && getListMap.size() > 0) {
            quaMap.put("data", getListMap);
        }
        quaListMap.add(quaMap);
    }

    /**
     * 根据关键字查询资质
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getFilterQual(Map<String, Object> param) {
        String bizType = MapUtils.getString(param, "bizType");
        List<Map<String, Object>> benchQualList = this.getBenchQual(bizType);
        //查找符合条件资质
        List<Map<String, Object>> filterQual = this.indexOfKeyWord(benchQualList, MapUtils.getString(param, "keyWord"));
        if (null == filterQual || filterQual.size() == 0) {
            return filterQual;
        }
        //一级资质
        Map<String, Object> valMap = new HashedMap(1);
        valMap.put("level", 1);
        valMap.put("bizType", bizType);
        Map<String, Map<String, Object>> cateQual = this.getQual(valMap);
        valMap.put("level", 2);
        Map<String, Map<String, Object>> levelTwoQual = this.getQual(valMap);
        valMap.put("level", 3);
        Map<String, Map<String, Object>> levelThreeQual = this.getQual(valMap);
        //二级资质
        Map<String, List<Map<String, Object>>> quals = new HashedMap(cateQual.size());
        StringBuffer level;
        StringBuffer parentId;
        //将标准资质分类
        for (Map<String, Object> map : filterQual) {
            level = new StringBuffer(MapUtils.getString(map, "quaLevel"));
            parentId = new StringBuffer(MapUtils.getString(map, "parentId"));
            if ("2".equals(level.toString())) {
                setQualMap(parentId.toString(), quals, map);
            } else if ("3".equals(level.toString())) {
                Map<String, Object> twoQual = levelTwoQual.get(parentId.toString());
                Map<String, Object> oneQual = cateQual.get(MapUtils.getString(twoQual, "parentId"));
                setQualMap(MapUtils.getString(oneQual, "id"), quals, map);
            } else if ("4".equals(level.toString())) {
                Map<String, Object> threeQual = levelThreeQual.get(parentId.toString());
                Map<String, Object> twoQual = levelTwoQual.get(MapUtils.getString(threeQual, "parentId"));
                Map<String, Object> oneQual = cateQual.get(MapUtils.getString(twoQual, "parentId"));
                setQualMap(MapUtils.getString(oneQual, "id"), quals, map);
            }
        }
        List<Map<String, Object>> list = new ArrayList<>(quals.size());
        Map<String, Object> qualMap;
        Map<String, Object> value;
        //格式化资质
        for (Map.Entry<String, List<Map<String, Object>>> entry : quals.entrySet()) {
            if (null != cateQual.get(entry.getKey()) && null != entry.getValue() && entry.getValue().size() > 0) {
                value = cateQual.get(entry.getKey());
                for (Map<String, Object> map : entry.getValue()) {
                    qualMap = new HashedMap(2);
                    qualMap.put("quaCode", value.get("quaCode") + "-" + map.get("quaCode"));
                    qualMap.put("quaName", value.get("quaName") + "-" + map.get("benchName"));
                    list.add(qualMap);
                }
            }
        }
        return list;
    }

    /**
     * 获取资质
     *
     * @param param
     * @return
     */
    private Map<String, Map<String, Object>> getQual(Map<String, Object> param) {
        String key = "qual_" + ObjectUtils.buildMapParamHash(param);
        Object resultQua = RedisShardedPoolUtil.get(key);
        if (null != resultQua) {
            return (Map<String, Map<String, Object>>) resultQua;
        }
        List<Map<String, Object>> list = dicQuaMapper.queryQual(param);
        Map<String, Map<String, Object>> valMap = new LinkedHashMap(list.size());
        for (Map<String, Object> map : list) {
            valMap.put(MapUtils.getString(map, "id"), map);
        }
        if (MapUtils.isNotEmpty(valMap)) {
            RedisShardedPoolUtil.setEx(key, valMap, 3600);
        }
        return valMap;
    }

    /**
     * 获取标准名称资质
     *
     * @return
     */
    private List<Map<String, Object>> getBenchQual(String bizType) {
        String key = "bench_qual_" + bizType;
        Object resultQua = RedisShardedPoolUtil.get(key);
        if (null != resultQua) {
            return (List<Map<String, Object>>) resultQua;
        }
        List<Map<String, Object>> list = dicQuaMapper.queryBenchQual(bizType);
        RedisShardedPoolUtil.setEx(key, list, 3600);
        return list;
    }

    /**
     * 筛选出符合关键字的资质
     *
     * @param benchQuals
     * @param keyWords
     * @return
     */
    private List<Map<String, Object>> indexOfKeyWord(List<Map<String, Object>> benchQuals, String keyWords) {
        if (StringUtils.isEmpty(keyWords)) {
            return benchQuals;
        }
        List<Map<String, Object>> resultList = new ArrayList<>(benchQuals.size());
        for (Map<String, Object> map : benchQuals) {
            if (MapUtils.getString(map, "benchName").indexOf(keyWords) > -1) {
                resultList.add(map);
                continue;
            }
        }
        benchQuals = null;
        return resultList;
    }

    /**
     * 设置资质map
     */
    private void setQualMap(String key, Map<String, List<Map<String, Object>>> quals, Map<String, Object> value) {
        List<Map<String, Object>> list;
        if (null != quals.get(key)) {
            list = quals.get(key);
            list.add(value);
        } else {
            list = new ArrayList<>(1);
            list.add(value);
        }
        quals.put(key, list);
        list = null;
    }
}
