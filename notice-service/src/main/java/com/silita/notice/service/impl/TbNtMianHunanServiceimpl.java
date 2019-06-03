package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.dao.TbNtMianHunanMapper;
import com.silita.notice.model.TbNtMianHunan;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@Service("notice")
public class TbNtMianHunanServiceimpl implements TbNtMianHunanService {
    @Autowired
    private TbNtMianHunanMapper tbNtMianHunanMapper;

    private String hBaseTableName="notice";

    @Autowired
    private Connection connection;

    /**
     * 查询中标公告
     * @param
     * @return
     */
    @Override
    public List<Map<String,Object>> queryBids(Map<String,Object> param) {
        Map<String,Object> resultMap = new HashMap<String,Object>();

        //获取地区
        String regions = MapUtils.getString(param, "regions");
        System.out.println(regions);

        String[] split = regions.split("\\|\\|");
            //获取省
        String proviceCode = split[0].toString();
        param.put("proviceCode",proviceCode);
        try{
            if(null != split[1] && "" != split[1]){
                String addrs = split[1].toString();
                //获取市
                String[] split1 = addrs.split(",");
                List<String> cityCodeList = Arrays.asList(split1);
                param.put("cityCodeList",cityCodeList);
            }
            else{
                List<String> cityCodeList =null;
                param.put("cityCodeList",cityCodeList);
            }

        }
        catch (Exception e){
            System.out.println("split[1] = null");
        }

        return tbNtMianHunanMapper.queryBids(param);
    }

    /**
     * 通过公司名称模糊查询公司中标记录
     * 全国
     */
    @Override
    public List<Map<String,Object>> queryCompanyName(String companyName) {
        return tbNtMianHunanMapper.queryCompanyName(companyName);
    }

    /**
     * 查询招标
     * @param param
     * @return
     */
    @Override
    public PageInfo queryTenders(Map<String,Object> param) {
        //获取地区
        String regions = MapUtils.getString(param, "regions");

        String[] split = regions.split("\\|\\|");
        if (null != split && split.length == 1){
            //获取省
            param.put("proviceCode",split[0]);
        }else  if (split.length == 2){
            //获取省
            param.put("proviceCode",split[0]);
            String addrs = split[1];
            //获取市
            String[] split1 = addrs.split(",");
            List<String> cityCodeList = Arrays.asList(split1);
            param.put("cityCodeList",cityCodeList);
        }

        //获取评标法
        String pbModes = MapUtils.getString(param, "pbModes");
        if(StringUtils.isNotEmpty(pbModes)){
            String[] split2 = pbModes.split("\\|\\|");
            List<String> pbModeList = Arrays.asList(split2);
            param.put("pbModeList",pbModeList);
        }

        String zzType = MapUtils.getString(param, "zzType");

        String[] zz = zzType.split("\\,");
        String zz1 = zz[0].toString();
        String zz2 = zz[1].toString();
        String zz3 = zz[2].toString();

        //资质 1
        String[] spdj = zz1.split("\\/");


        String zzdj1 = spdj[0].toString();
        String zzdj2 = spdj[1].toString();


        // 资质 2
        String[] zz2dj = zz2.split("\\/");

        String zz2dj1 = spdj[0].toString();
        String zz2dj2 = spdj[1].toString();


        // 资质 3
        String[] zz3dj = zz3.split("\\/");

        String zz3dj1 = spdj[0].toString();
        String zz3dj2 = spdj[1].toString();

        param.put("zzdj1",zzdj1);
        param.put("zzdj2",zzdj2);
        param.put("zz2dj1",zz2dj1);
        param.put("zz2dj2",zz2dj2);
        param.put("zz3dj1",zz3dj1);
        param.put("zz3dj2",zz3dj2);

        List<String> regexList = tbNtMianHunanMapper.queryQuaId(param);
        param.put("regexList",regexList);

        param.put("pdModeType",param.get("proviceCode")+"_pbmode");

        PageHelper.startPage(1,20);
        List<Map<String,Object>> list = tbNtMianHunanMapper.queryTenders(param);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    /**
     * 获取中标详情
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryBidsNociteDetails(Map<String,Object> param) {
       /* String regions = MapUtils.getString(param, "regions");
        System.out.println(regions);

        String[] split = regions.split("\\|\\|");
        //获取省
        String source = split[0].toString();
        param.put("source",source);*/
        return tbNtMianHunanMapper.queryBidsNociteDetails(param);
    }

    /**
     * 获取招标详情
     * @param pkid
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTendersNociteDetails(String pkid) {
        return tbNtMianHunanMapper.queryTendersNociteDetails(pkid);
    }

    /**
     * 获取公告详情
     * @param rowId  // 爬取id
     *               返回Map
     * @return
     * @throws IOException
     */
    public Map<String,Object> queryBidsDetailsCentend(String rowId) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        TableName tableName = TableName.valueOf(hBaseTableName);
        Table table = connection.getTable(tableName);
        Get g = new Get(rowId.getBytes());
        Result rs = table.get(g);
        Cell[] cells = rs.rawCells();
        String rankStr = "";
        for (Cell cell : cells) {
            String key = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (key) {
                case "content": //获取内容
                    map.put("content",value);
                    break;
            }
        }
        return map;
    }

    /**
     * 获取公告详情
     * @param rowId  // 爬取id
     *               返回String类型
     * @return
     * @throws IOException
     */

    public String queryBidsDetailsCentendString(String rowId) throws IOException {
        String content="";
        Map<String,Object> map = new HashMap<String,Object>();
        TableName tableName = TableName.valueOf(hBaseTableName);
        Table table = connection.getTable(tableName);
        Get g = new Get(rowId.getBytes());
        Result rs = table.get(g);
        Cell[] cells = rs.rawCells();
        String rankStr = "";
        for (Cell cell : cells) {
            String key = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (key) {
                case "content": //获取内容
                    content=value;
                    break;
            }
        }
        return content;
    }

    /**
     * 获取评标办法
     * @param pbModes
     * @return
     */
    @Override
    public List<String> queryPbModes(String pbModes){
        String[] split = pbModes.split("\\|\\|");
        List<String> list = Arrays.asList(split);
        return list;
    }

    /**
     * 获取地区
     * @param regional
     * @return
     */
    @Override
    public Map<String,Object> queryRegional(String regional){
        Map<String,Object> map = new HashMap<String,Object>();
        String[] split = regional.split("\\|\\|");
        String province = split[0].toString();
        //System.out.println("pro:"+pro);
        try{
            //System.out.println("split[1]="+split[1]);
            String addrs = split[1].toString();
            //System.out.println("addrs:"+addrs);

            String[] split1 = addrs.split(",");

            List<String> list =
                    Arrays.asList(split1);
           /* for (String s1 : list) {
                System.out.println("s1:"+s1);
            }*/

            map.put("province",province);
            map.put("city",list);
        }catch (Exception e){
            System.out.println("split[1] = null");
        }



        return map;
    }

    /**
     * 点击量
     * @param param
     * @return
     */
    @Override
    public Map<String,Object> queryClickCount(Map<String,Object> param) {
        return tbNtMianHunanMapper.queryClickCount(param);
    }

    /**
     * 获取点击量
     * @param param
     * @return
     */
    @Override
    public Integer count(Map<String,Object> param) {
        Map<String, Object> map = tbNtMianHunanMapper.queryClickCount(param);
        Integer clickCount = (Integer) map.get("clickCount");
        System.out.println(clickCount);
        return clickCount;
    }

    /**
     * 匹配用户是否关注
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> queryattention(Map<String,Object> param) {
        return tbNtMianHunanMapper.queryattention(param);
    }

    /**
     * 是否关注
     * @param param
     * @return
     */
    @Override
    public Boolean attention(Map<String,Object> param) {
        boolean collected=false;
        Map<String, Object> queryattention = tbNtMianHunanMapper.queryattention(param);
        if (null != queryattention){
            collected=true;
        }
        return collected;
    }

    /**
     * 通过资质获取资质等级表的id
     * @param param
     * @return
     */
    @Override
    public List<String> queryQuaId(Map<String, Object> param) {
     /*   String zzType = MapUtils.getString(param, "zzType");

        String[] zz = zzType.split("\\,");
        String zz1 = zz[0].toString();
        String zz2 = zz[1].toString();
        String zz3 = zz[2].toString();

        //资质 1
        String[] spdj = zz1.split("\\/");


        String zzdj1 = spdj[0].toString();
        String zzdj2 = spdj[1].toString();


        // 资质 2
        String[] zz2dj = zz2.split("\\/");

        String zz2dj1 = spdj[0].toString();
        String zz2dj2 = spdj[1].toString();


        // 资质 3
        String[] zz3dj = zz3.split("\\/");

        String zz3dj1 = spdj[0].toString();
        String zz3dj2 = spdj[1].toString();

        param.put("zzdj1",zzdj1);
        param.put("zzdj2",zzdj2);
        param.put("zz2dj1",zz2dj1);
        param.put("zz2dj2",zz2dj2);
        param.put("zz3dj1",zz3dj1);
        param.put("zz3dj2",zz3dj2);*/

        return tbNtMianHunanMapper.queryQuaId(param);
    }

   /* *//**
     * 通过资质等级id  找到对应公告id
     * @param param
     * @return
     *//*
    @Override
    public List<String> queryZzgxId(Map<String, Object> param) {
        MapUtils.getString(param,"regexList");
        return tbNtMianHunanMapper.queryZzgxId(param);
    }
*/
    /**
     * 获取资质关系表中的公告
     * @param param
     * @return
     */
/*    @Override
    public List<String> queryZzPkid(Map<String,Object> param){
*//*        String zzType = MapUtils.getString(param, "zzType");

        String[] zz = zzType.split("\\,");
        String zz1 = zz[0].toString();
        String zz2 = zz[1].toString();
        String zz3 = zz[2].toString();

        //资质 1
        String[] spdj = zz1.split("\\/");


        String zzdj1 = spdj[0].toString();
        String zzdj2 = spdj[1].toString();


        // 资质 2
        String[] zz2dj = zz2.split("\\/");

        String zz2dj1 = spdj[0].toString();
        String zz2dj2 = spdj[1].toString();


        // 资质 3
        String[] zz3dj = zz3.split("\\/");

        String zz3dj1 = spdj[0].toString();
        String zz3dj2 = spdj[1].toString();

        param.put("zzdj1",zzdj1);
        param.put("zzdj2",zzdj2);
        param.put("zz2dj1",zz2dj1);
        param.put("zz2dj2",zz2dj2);
        param.put("zz3dj1",zz3dj1);
        param.put("zz3dj2",zz3dj2);

        List<String> regexList = tbNtMianHunanMapper.queryQuaId(param);*//*
       *//* for (String s : regexList) {
            System.out.println(s);
        }

        param.put("regexList",regexList);

        List<String> pkidList = tbNtMianHunanMapper.queryZzgxId(param);
        for (String s : pkidList) {
            System.out.println(s);
        }*//*

        return pkidList;


    }*/


    /**
     * 获取资质
     * @param notice
     * @return
     */
    public String queryNocite(String notice){
        String notices = "";

        return notice;
    }


    /**
     * 获取点击量
     * @param pkid
     * @return
     */
    public Integer getClickCount(String pkid){
        //获取标题
        //String title = MapUtils.getString(param, "title");
         /*//获取金额
        String projSumStart = MapUtils.getString(param, "projSumStart");
        String projSumEnd = MapUtils.getString(param, "projSumStart");*/
        //param.put("proviceCode",proviceCode);
        /*param.put("title",title);
        param.put("projSumStart",projSumStart);
        param.put("projSumEnd",projSumStart);*/
        return 0;

    }


}
