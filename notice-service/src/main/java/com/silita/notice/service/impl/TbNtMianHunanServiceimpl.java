package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.IsNullCommon;
import com.silita.notice.common.RangeCommon;
import com.silita.notice.common.RegionCommon;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.*;
import com.silita.notice.elasticsearch.ElasticsearchFactory;
import com.silita.notice.model.es.NoticeElasticsearch;
import com.silita.notice.service.TbNtMianHunanService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("notice")
public class TbNtMianHunanServiceimpl implements TbNtMianHunanService {
    @Autowired
    private TbNtMianHunanMapper tbNtMianHunanMapper;
    @Autowired
    private TbCompanyMapper tbCompanyMapper;
    @Autowired
    private TbCommentInfoMapper tbCommentInfoMapper;
    @Value("${hbase.notice-table-name}")
    private String hBaseTableName;
    @Autowired
    private Connection connection;
    @Autowired
    private RelQuaGradeMapper relQuaGradeMapper;
    @Autowired
    TbNtAssociateGpMapper tbNtAssociateGpMapper;
    @Autowired
    TbNtRegexQuaMapper tbNtRegexQuaMapper;
    @Autowired
    ElasticsearchFactory elasticsearchFactory;

    /**
     * 查询中标公告
     *
     * @param
     * @return
     */
    @Override
    public PageInfo queryBids(Map<String, Object> param) {
        IsNullCommon.isNull(param);
        //获取地区
        queryRegions(param);
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, String>> data = tbNtMianHunanMapper.queryBids(param);
       /* if (data != null && data.size() > 0) {
            String key;
            for (Map<String, Object> map : data) {
                if (null != map.get("oneName")) {
                    param.put("comName", map.get("oneName"));
                    key = RedisConstantInterface.NOTIC_LAW + ObjectUtils.buildMapParamHash(param);
                    if (RedisShardedPoolUtil.keyExist(key)) {
                        map.put("oneLaw", "1");
                    }
                }
            }
        }*/
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过公司名称模糊查询公司中标记录
     * 全国
     */
    @Override
    public PageInfo queryCompanyName(Map<String, Object> param) {
        //判断传参是否为空
        IsNullCommon.isNull(param);
        Map<String, Object> typeMap = new HashMap<>();
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, String>> list = tbNtMianHunanMapper.queryCompanyName(param);
        for (Map<String, String> map : list) {
           /* String key;
            if (null != map.get("oneName")) {
                param.put("comName", map.get("oneName"));
                key = RedisConstantInterface.NOTIC_LAW + ObjectUtils.buildMapParamHash(param);
                if (RedisShardedPoolUtil.keyExist(key)) {
                    map.put("oneLaw", "1");
                }
            }*/
            typeMap.put("source", map.get("source"));
            typeMap.put("ntId", map.get("id"));
            Map<String, Object> map1 = tbNtMianHunanMapper.queryProjectTypeNoticeType(typeMap);
            map.put("projectType", MapUtils.getString(map1, "projectType"));
            map.put("noticeType", MapUtils.getString(map1, "noticeType"));
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public Integer queryCompanyCount(Map<String, Object> param) {
        Integer count = tbNtMianHunanMapper.queryCompanyCount(param);
        return count;
    }

    /**
     * 查询招标
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo queryTenders(Map<String, Object> param) {
        IsNullCommon.isNull(param);
        //获取地区
        queryRegions(param);
        //获取评标法
        String pbModes = MapUtils.getString(param, "pbModes");
        if (StringUtils.isNotEmpty(pbModes)) {
            String[] split2 = pbModes.split("\\|\\|");
            List<String> pbModeList = Arrays.asList(split2);
            param.put("pbModeList", pbModeList);
        }
        //获取资质
        String zzType = MapUtils.getString(param, "zzType");
        if (StringUtils.isNotEmpty(zzType)) {
            String rangeType = MapUtils.getString(param, "rangeType");
            if (StringUtils.isEmpty(rangeType)) {
                rangeType = "or";
                param.put("rangeType", rangeType);
            }

            List<String> regexList = setNoticeQual(zzType);
            //如果rangeType为空则给他赋默认值为or
            if (rangeType.equals("or")) {
                param.put("regexList", regexList);
            } else if (rangeType.equals("and")) {
                //如果rangeType为and 则先排序，再把regexList赋值给quaRegex
                String quaRegex = "";
                Collections.sort(regexList);
                for (String id : regexList) {
                    quaRegex = quaRegex + id;
                }
                param.put("quaRegex", quaRegex);
            }
        }

        param.put("pdModeType", param.get("proviceCode") + "_pbmode");

        String comName = MapUtils.getString(param, "comName");
        if (StringUtils.isNotEmpty(comName)) {
            String comId = tbCompanyMapper.queryComId(param);
            List<String> comNameRangeQual = tbCompanyMapper.queryComNameRangeQual(comId);
            if (comNameRangeQual != null && comNameRangeQual.size() > 0) {
                //处理企业资质问题
                param.put("aptitudeUuidList", setQualCondition(comNameRangeQual));
            } else {
                param.put("title", comName);
            }
        }

        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = tbNtMianHunanMapper.queryTenders(param);
        for (Map<String, Object> map : list) {
            String zzRank = (String) map.get("certificate");
            if (StringUtils.isNotEmpty(zzRank)) {
                zzRank = zzRank.replaceAll("(?:和|或)", ",");
                map.put("certificate", zzRank);
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 获取中标详情
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryBidsNociteDetails(Map<String, Object> param) {
        Map<String, Object> map = tbNtMianHunanMapper.queryBidsNociteDetails(param);
        /*String key;
        if (null != map.get("oneName") && "" != map.get("oneName")) {
            param.put("comName", map.get("oneName"));
            key = RedisConstantInterface.NOTIC_LAW + ObjectUtils.buildMapParamHash(param);
            if (RedisShardedPoolUtil.keyExist(key)) {
                map.put("oneLaw", "1");
            }
        }*/
        Integer commentCount = tbCommentInfoMapper.queryCountComment(param);
        map.put("commentCount", commentCount);
        return map;
    }

    /**
     * 获取招标详情
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryTendersNociteDetails(Map<String, Object> param) {
        Map<String, Object> map = tbNtMianHunanMapper.queryTendersNociteDetails(param);
        String zzRank = MapUtils.getString(map, "zzRank");
        if (StringUtils.isNotEmpty(zzRank)) {
            zzRank = zzRank.replaceAll("(?:和|或)", ",");
            map.put("zzRank", zzRank);
        }
        Integer commentCount = tbCommentInfoMapper.queryCountComment(param);
        if (map != null && map.size() > 0) {
            map.put("commentCount", commentCount);
        }
        return map;
    }


    /**
     * 获取公告详情
     *
     * @param param // 爬取id
     *              返回String类型
     * @return
     * @throws IOException
     */
    public String queryBidsDetailsCentendString(Map<String, Object> param) throws IOException {
        String snatchId = MapUtils.getString(param, "snatchId");
        String content = "";
        Map<String, Object> map = new HashMap<String, Object>();
        TableName tableName = TableName.valueOf(hBaseTableName);
        Table table = connection.getTable(tableName);
        Get g = new Get(snatchId.getBytes());
        Result rs = table.get(g);
        Cell[] cells = rs.rawCells();
        for (Cell cell : cells) {
            String key = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (key) {
                case "content": //获取内容
                    content = value;
                    break;
            }
        }
        return content;
    }

    /**
     * 获取评标办法
     *
     * @param pbModes
     * @return
     */
    @Override
    public List<String> queryPbModes(String pbModes) {
        String[] split = pbModes.split("\\|\\|");
        List<String> list = Arrays.asList(split);
        return list;
    }


    /**
     * 公共地区
     */
    private void queryRegions(Map<String, Object> param) {
        //获取地区
        String regions = MapUtils.getString(param, "regions");
        if (null != regions && !"".equals(regions)) {
            String[] split = regions.split("\\|\\|");
            if (null != split && split.length == 1) {
                //获取省
                param.put("proviceCode", split[0]);
            } else if (split.length == 2) {
                //获取省
                param.put("proviceCode", split[0]);
                String addrs = split[1];
                //获取市
                String[] split1 = addrs.split(",");

                List<String> cityCodeList = Arrays.asList(split1);
                param.put("cityCodeList", cityCodeList);
            }
        } else {
            //默认地区
            param.put("proviceCode", "hunan");
        }
    }


    /**
     * 获取地区
     *
     * @param regional
     * @return
     */
    @Override
    public Map<String, Object> queryRegional(String regional) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] split = regional.split("\\|\\|");
        if (null != split && split.length >= 2) {
            String province = split[0].toString();
            String addrs = split[1].toString();
            String[] split1 = addrs.split(",");
            List<String> list =
                    Arrays.asList(split1);
            map.put("province", province);
            map.put("city", list);
        } else if (split.length < 2 && split.length > 0) {
            String province = split[0].toString();
            map.put("province", province);
        }
        return map;
    }


    /**
     * 获取点击量
     *
     * @param param
     * @return
     */
    @Override
    public Integer count(Map<String, Object> param) {
        Integer clickCount = tbNtMianHunanMapper.queryClickCount(param);
        if (null != clickCount) {
            clickCount++;
            //点击量+1
            param.put("addCount", clickCount);
            tbNtMianHunanMapper.addClickCount(param);
            return clickCount;
        }
        clickCount = 1;
        param.put("clickCount", clickCount);
        tbNtMianHunanMapper.createClickCount(param);
        return clickCount;
    }

    /**
     * 匹配用户是否关注
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryAttention(Map<String, Object> param) {
        return tbNtMianHunanMapper.queryAttention(param);
    }

    /**
     * 是否关注
     *
     * @param param
     * @return
     */
    @Override
    public Boolean attention(Map<String, Object> param) {
        param.put("userId", VisitInfoHolder.getUserId());
        boolean collected = false;
        Map<String, Object> queryattention = tbNtMianHunanMapper.queryAttention(param);
        if (null != queryattention) {
            collected = true;
        }
        return collected;
    }

    /**
     * 通过资质获取资质等级表的id
     *
     * @param param
     * @return
     */
    @Override
    public List<String> queryQuaId(Map<String, Object> param) {
        return tbNtMianHunanMapper.queryQuaId(param);
    }

    /**
     * 变更点击量
     *
     * @param param
     */
    @Override
    public void addClickCount(Map<String, Object> param) {
        tbNtMianHunanMapper.addClickCount(param);
    }

    /**
     * 查省级编号和市级编号和爬取id
     */
    @Override
    public Map<String, Object> queryProviceCity(Map<String, Object> param) {
        return tbNtMianHunanMapper.queryProviceCity(param);
    }

    /**
     * 通过编号查询省级名称和市名称
     */
    @Override
    public Map<String, Object> queryProviceName(Map<String, Object> param) {
        return tbNtMianHunanMapper.queryProviceName(param);
    }

    /**
     * 通过编号查询市级名称
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryCityName(Map<String, Object> param) {
        return tbNtMianHunanMapper.queryCityName(param);
    }

    @Override
    public List<String> getNoticeCountList(Map<String, Object> param) {
        if (null == param.get("source")) {
            param.put("source", "湖南省");
        }
        Object source = RegionCommon.regionSourcePinYin.get(MapUtils.getString(param, "source"));
        if (null == source) {
            source = "hunan";
        }
        //格式化地区
        param.put("source", source);
        return tbNtMianHunanMapper.querySnatchId(param);
    }

    @Override
    public List<Map<String, Object>> listNoticeCorrelation(Map<String, Object> param) {
        return tbNtAssociateGpMapper.queryNoticeCorrelationList(param);
    }

    @Override
    public Map<String, Object> getSubscribeList(Map<String, Object> param) {
        return null;
    }

    @Override
    public void importNoticeForEs() throws IOException {
        Map<String, Object> param = new HashMap<>();
        for (Object val : RegionCommon.regionSourcePinYin.values()) {
            param.put("source", val);
            param.put("pdModeType", val + "_pbmode");
            List<NoticeElasticsearch> list = tbNtMianHunanMapper.queryNotice(param);
            if (null != list && list.size() > 0) {
                for (NoticeElasticsearch noce : list) {
                    param = new HashMap<>();
                    param.put("snatchId", noce.getSnatchId());
                    noce.setQuaId(tbNtRegexQuaMapper.queryCateQuaId(noce.getNtId()));
                    noce.setContent(Jsoup.parse(queryBidsDetailsCentendString(param)).text());
                }
                elasticsearchFactory.saveAll(list);
            }
        }
    }

    /**
     * 符合企业资质的招标公告（资质处理）
     */
    private List<String> setQualCondition(List<String> comQual) {
        List<String> newQuals = new ArrayList<>();
        List<Map<String, Object>> qualGradeList = relQuaGradeMapper.queryQuaCodeGradeCode(new HashedMap(1) {{
            put("list", comQual);
        }});
        StringBuffer qual;
        StringBuffer grade;
        Map<String, Object> valMap = new HashedMap(2);
        for (Map<String, Object> qualGrade : qualGradeList) {
            qual = new StringBuffer(MapUtils.getString(qualGrade, "quaCode"));
            grade = new StringBuffer(MapUtils.getString(qualGrade, "gradeCode"));
            valMap.put("quaCode", qual.toString());
            if ("grade_tj_1553497710814".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_tj_1553497710814");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_yj_1553245789202");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_yjjys_1554256688540");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_ej_1553245789219");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_ejjys_1554256688469");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553245789236");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688485");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553476160640");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688494");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wj_1553476160663");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wjjys_1554256688504");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_yj_1553245789202".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_yj_1553245789202");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_yjjys_1554256688540");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_ej_1553245789219");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_ejjys_1554256688469");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553245789236");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688485");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553476160640");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688494");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wj_1553476160663");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wjjys_1554256688504");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_ej_1553245789219".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_ej_1553245789219");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_ejjys_1554256688469");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553245789236");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688485");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553476160640");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688494");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wj_1553476160663");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wjjys_1554256688504");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_sj_1553245789236".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_sj_1553245789236");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688485");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sj_1553476160640");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688494");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wj_1553476160663");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wjjys_1554256688504");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_sj_1553476160640".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_sj_1553476160640");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_sjjys_1554256688494");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wj_1553476160663");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wjjys_1554256688504");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_wj_1553476160663".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_wj_1553476160663");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_wjjys_1554256688504");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_jj_1553245789116".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_jj_1553245789116");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_yj_1553245789137");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_yjjys_1554256688513");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_bj_1553245789155");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_bjjys_1554256688521");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_dj_1553245789183");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_djjys_1554256688530");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_yj_1553245789137".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_yj_1553245789137");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_yjjys_1554256688513");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_bj_1553245789155");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_bjjys_1554256688521");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_dj_1553245789183");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_djjys_1554256688530");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_bj_1553245789155".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_bj_1553245789155");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_bjjys_1554256688521");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_dj_1553245789183");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_djjys_1554256688530");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else if ("grade_dj_1553245789183".equals(grade.toString())) {
                valMap.put("gradeCode", "grade_dj_1553245789183");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
                valMap.put("gradeCode", "grade_djjys_1554256688530");
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            } else {
                valMap.put("gradeCode", grade.toString());
                newQuals.add(relQuaGradeMapper.queryId(valMap));
            }
        }
        List<String> resultQuals = new ArrayList<>();
        for (String str : newQuals) {
            if (StringUtils.isNotEmpty(str)) {
                resultQuals.add(str);
            }
        }
        return resultQuals;
    }

    @Override
    public List<String> setNoticeQual(String zzType) {
        List<Map<String, Object>> group = new ArrayList<Map<String, Object>>();
        String[] zz = zzType.split(",");
        Map<String, Object> maps;
        for (String z : zz) {
            maps = new HashMap<>();
            String[] split1 = z.split("/");
            if (split1.length >= 2) {
                if (null != RangeCommon.splitRange.get(split1[1])) {
                    String[] grades = RangeCommon.splitRange.get(split1[1]).split(",");
                    for (String str : grades) {
                        maps = new HashedMap();
                        maps.put("quaCode", split1[0]);
                        maps.put("gradeCode", str);
                        group.add(maps);
                    }
                } else {
                    maps.put("quaCode", split1[0]);
                    maps.put("gradeCode", split1[1]);
                    group.add(maps);
                }
            } else {
                maps.put("quaCode", split1[0]);
                maps.put("gradeCode", "0");
                group.add(maps);
            }
        }
        Map val = new HashedMap(1) {{
            put("groupList", group);
        }};
        return tbNtMianHunanMapper.queryQuaId(val);
    }

    @Override
    public void setNoticeReadStatus(Map<String, Object> param) {
        String userId = VisitInfoHolder.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return;
        }
        param.put("userId", userId);
        int count = tbNtMianHunanMapper.queryNoticeReadStatus(param);
        if (count > 0) {
            return;
        }
        tbNtMianHunanMapper.insertNoticeRead(param);
    }
}
