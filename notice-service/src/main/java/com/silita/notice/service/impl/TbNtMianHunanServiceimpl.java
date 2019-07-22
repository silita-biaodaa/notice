package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.TbCommentInfoMapper;
import com.silita.notice.dao.TbCompanyMapper;
import com.silita.notice.dao.TbNtMianHunanMapper;
import com.silita.notice.dao.TbNtRegexQuaMapper;
import com.silita.notice.service.TbNtMianHunanService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
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

    @Autowired
    private TbNtRegexQuaMapper tbNtRegexQuaMapper;

    @Value("${hbase.notice-table-name}")
    private String hBaseTableName;
    @Autowired
    private Connection connection;

    /**
     * 查询中标公告
     *
     * @param
     * @return
     */
    @Override
    public PageInfo queryBids(Map<String, Object> param) {
        //获取地区
        queryRegions(param);
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> data = tbNtMianHunanMapper.queryBids(param);
        if (data != null && data.size() > 0) {
            /*String key;
            for (Map<String, Object> map : data) {
                if (null != map.get("oneName")) {
                    param.put("comName", map.get("oneName"));
                    key = RedisConstantInterface.NOTIC_LAW + ObjectUtils.buildMapParamHash(param);
                    if (RedisShardedPoolUtil.keyExist(key)) {
                        map.put("oneLaw", "1");
                    }
                }
            }*/
        }
        PageInfo pageInfo = new PageInfo(data);
        return pageInfo;
    }

    /**
     * 通过公司名称模糊查询公司中标记录
     * 全国
     */
    @Override
    public PageInfo queryCompanyName(Map<String, Object> param) {
        Map<String, Object> typeMap = new HashMap<>();
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = tbNtMianHunanMapper.queryCompanyName(param);
        for (Map<String, Object> map : list) {
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
            map.put("projectType", map1.get("projectType"));
            map.put("noticeType", map1.get("noticeType"));
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
        List<Map<String, Object>> group = new ArrayList<Map<String, Object>>();
        String zzType = MapUtils.getString(param, "zzType");
        String rangeType = MapUtils.getString(param, "rangeType");
        String[] zz = zzType.split("\\,");
        for (String z : zz) {
            Map<String, Object> maps = new HashMap<String, Object>();
            String[] split1 = z.split("\\/");
            if (split1.length >= 2) {
                maps.put("quaCode", split1[0]);
                maps.put("gradeCode", split1[1]);
            } else {
                maps.put("quaCode", split1[0]);
                maps.put("gradeCode", "");
            }
            group.add(maps);
        }
        param.put("groupList", group);

        //获取资质id
        List<String> regexList = tbNtMianHunanMapper.queryQuaId(param);
        //如果rangeType为空则给他赋默认值为or
        if (StringUtils.isBlank(rangeType)) {
            param.put("rangeType", "or");
            param.put("regexList", regexList);
        } else if (rangeType.equals("or")) {
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

        param.put("pdModeType", param.get("proviceCode") + "_pbmode");

        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);
        String comName = MapUtils.getString(param, "comName");

        if (StringUtils.isNotEmpty(comName)) {
            String s1 = tbCompanyMapper.queryComNameQual(param);
            param.put("range", s1);
           /* List<String> listNtId = tbNtRegexQuaMapper.queryListNoticeId(param);
            param.put("listNtId", listNtId);*/

        }

        List<Map<String, Object>> list = tbNtMianHunanMapper.queryTenders(param);
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
        Integer commentCount = tbCommentInfoMapper.queryCountComment(param);
        map.put("commentCount", commentCount);
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

    public void queryRegions(Map<String, Object> param) {
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


}
