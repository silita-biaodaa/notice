package com.silita.notice.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.model.TbNtMianHunan;
import com.silita.notice.service.TbNtMianHunanService;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hbase.thirdparty.org.apache.commons.collections4.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/nocite")
public class NociteController extends BaseController {
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;

    private String hBaseTableName="notice";

    @Autowired
    private Connection connection;

    /**
     * 查询公告  -  中标
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zhongbiao/list",method = RequestMethod.POST)
    public Map queryBids(@RequestBody Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        PageHelper.startPage(1,20);
        List<Map<String, Object>> maps = tbNtMianHunanService.queryBids(param);
        PageInfo pageInfo = new PageInfo(maps);
        seccussMap(resultMap,pageInfo);
        return resultMap;
    }





    /**
     *前端：精确企业名称
     *后台：通过获取企业名称 模糊匹配中标的公告
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/company/zhongbiao/list",method = RequestMethod.POST)
    public Map queryCompanyName(Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String companyName = MapUtils.getString(param,"comName");
        Integer pageNo = MapUtils.getInteger(param,"pageNo");
        List<Map<String,Object>> list = tbNtMianHunanService.queryCompanyName(companyName);
        PageInfo pageInfo = new PageInfo(list);
        seccussMap(resultMap,pageInfo);
        return resultMap;
    }

    /**
     * 查询招标信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zhaobiao/list",method = RequestMethod.POST)
    public Map queryTenders(@RequestBody Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        PageInfo pageInfo = tbNtMianHunanService.queryTenders(param);
        seccussMap(resultMap,pageInfo);
        return resultMap;
    }

    /**
     * 获取中标详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zhongbiao/bidsDetails",method = RequestMethod.POST)   //用户是否关注  **
    public Map bidsDetails(@RequestBody Map<String,Object> param) throws IOException {
        System.out.println(param);
        String rowId = MapUtils.getString(param, "rowId");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //获取点击量
        Integer count = tbNtMianHunanService.count(param);//
        //获取是否关注
        Boolean attention = tbNtMianHunanService.attention(param);
        //Map<String,Object> coutnt = new HashMap<String,Object>();
        Map<String, Object> map1 = tbNtMianHunanService.queryBidsNociteDetails(param);
        String content = tbNtMianHunanService.queryBidsDetailsCentendString(rowId);
        map1.put("content",content);
        //map1.put("clickCount",count);
        map1.put("collected",attention);
        resultMap.put("clickCount",count);
        seccussMap(resultMap,map1);
        return resultMap;
    }






    /**
     * 获取招标详情
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tenders/bidsDetails",method = RequestMethod.POST)   //用户是否关注  **
    public Map tendersDetails(Map<String,Object> param) throws IOException {
        String rowId = MapUtils.getString(param, "rowId");
        String pkid = MapUtils.getString(param,"pkid");
        String userid=MapUtils.getString(param,"userid");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //用户是否关注
        List<Map<String, Object>> list = tbNtMianHunanService.queryTendersNociteDetails(pkid);
        Map<String, Object> map = tbNtMianHunanService.queryBidsDetailsCentend(rowId);
        list.add(map);
        resultMap.put("data",list);
        return resultMap;

    }



    public Map ss(Map<String,Object> param){
        Map<String,Object> map = new HashedMap<String,Object>();

        String regions = MapUtils.getString(param, "regions");

        String[] split = regions.split("\\||\\,");
        for (String s : split) {
            System.out.println();
        }
        String province = split[0].toString(); // 获取省
        System.out.println("pro:"+province);
        String city1 = "";
        String city2 = "";
        String city3 = "";
        try{
            if(null != split[2] && "" != split[2]){
                city1=split[2].toString();
            }
        }catch (Exception e1){
            System.out.println("city1 == null ");
        }

        try{
            if (null != split[3] && "" != split[3]){
                city2=split[3].toString();
            }
        }catch (Exception e2){
            System.out.println("city2 == null");
        }

        try{

            if (null != split[4] && "" != split[4]){
                city3=split[4].toString();
            }

        }catch (Exception e){
            System.out.println("city3 == null");

        }

        System.out.println("省："+province+"  市1："+city1+"  市2："+city2+"  市3："+city3);

        return map;
    }




}
