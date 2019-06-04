package com.silita.notice.web.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.model.TbNtMianHunan;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.web.BaseCastTest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TbNtMianHunanServiceimplTest extends BaseCastTest {

    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;

    private TbNtMianHunan tbNtMianHunan = new TbNtMianHunan();

    /**
     * 查询公告   中标
     */
    @Test
    public void queryBids() {
        tbNtMianHunan.setProviceCode("hunan");
       /* PageHelper.startPage(1,20);
        List<Map<String, Object>> list = tbNtMianHunanService.queryBids(tbNtMianHunan);
        PageInfo pageInfo = new PageInfo(list);
        for (Map<String, Object> map : list) {
            System.out.println(map.toString());
        }*/
    }

    /**
     * 查询公告   获取企业名称 模糊查询企业中标公告
     */
    @Test
    public void queryCompanyName() {
      /*  PageHelper.startPage(1,20);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String companyName = "黑龙江省龙泉建筑工程监理有限公司";
        List<Map<String,Object>> list = tbNtMianHunanService.queryCompanyName(companyName);
        PageInfo pageInfo = new PageInfo(list);
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }*/
    }

    /**
     * 查询招标
     */
    @Test
    public void queryTenders(){
       /* PageHelper.startPage(1,20);
        tbNtMianHunan.setProviceCode("hunan");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String tbMode = "";
        List<Map<String,Object>> list = tbNtMianHunanService.queryTenders(tbNtMianHunan,tbMode);
        PageInfo pageInfo = new PageInfo(list);
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }*/
    }

    @Test
    public void queryContent(){


    }

    private String hBaseTableName="notice";

    @Autowired
    private Connection connection;

    @Test
    public void hbaseTest() throws IOException {
/*        String proviceCode="hunan";
        String pkid = "";
        String rowId="bde119d5831cbb30fc95f12e63fee67b";
        tbNtMianHunan.setSnatchId("bde119d5831cbb30fc95f12e63fee67b");
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
                case "content":
                    Map<String, Object> list = tbNtMianHunanService.queryBidsNociteDetails(pkid);
                    Map <String,Object> m = new HashMap<String,Object>();
                    m.put("content",value);
                    //list.add(m);
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("data",list);
                    map.put("content",value);
                    for (String s : map.keySet()) {
                        String o =(String) map.get(key);
                        System.out.println("key:"+s+"   value:"+o);
                    }
                    break;
                case "url":
                    System.out.println(value);
                    break;
            }
        }*/



    }

    @Test
    public void test(){

        String s = "湖南省||邵阳市,长沙市,岳阳市";

        String[] split = s.split(",");
        for (String s1 : split) {
            System.out.println(s1);
        }

        /*String s1 = split.toString();
        System.out.println(s1);*/


    }


    @Test
    public void addrs(){

        String s = "湖南省||邵阳市,长沙市,岳阳市";
        Map<String, Object> map = tbNtMianHunanService.queryRegional(s);
        Object province = map.get("province");
        System.out.println("province"+province);
        Object city = map.get("city");
        System.out.println("city:"+city);

    }

    @Test
    public void count(){

       /* String pkid = "00005d8e44234747be75fbf39a051656";

        Map<String, Object> map = tbNtMianHunanService.queryClickCount(pkid);

        Integer clickCount = (Integer) map.get("clickCount");

        System.out.println(clickCount);*/


    }

    @Test
    public void gz(){
       /* String pkid="14";
        String userid="987708fce42f11e5a77970ef5e0a1757";
        Boolean attention = tbNtMianHunanService.attention(pkid, userid);

        System.out.println("是否关注："+attention);*/

    }

    @Test
    public void biaodaabids(){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("","");
        param.put("","");
        param.put("","");
        param.put("","");
        param.put("","");
        param.put("","");
        param.put("","");
        tbNtMianHunanService.queryBids(param);
    }







}