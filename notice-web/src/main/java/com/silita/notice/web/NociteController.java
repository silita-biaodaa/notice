package com.silita.notice.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.service.TbNtMianHunanService;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/nocite")
public class NociteController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;
    @Value("${hbase.notice-table-name}")
    private String hBaseTableName;
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
        //分页限制 最多到30页  每页20条记录
        checkPage(param);
        PageInfo pageInfo = tbNtMianHunanService.queryBids(param);
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
    public Map queryCompanyName(@RequestBody Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //分页限制 最多到30页  每页20条记录
        checkPage(param);
        PageInfo pageInfo = tbNtMianHunanService.queryCompanyName(param);
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
            checkPage(param);
            PageInfo pageInfo = tbNtMianHunanService.queryTenders(param);
            seccussMap(resultMap,pageInfo);
        return resultMap;

    }
    /**
     *公告详情
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nociteDetails",method = RequestMethod.POST)
    public Map queryNociteDetails(@RequestBody Map<String,Object> param) throws IOException {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //获取点击量
        Integer count = tbNtMianHunanService.count(param);
        //获取是否关注
        Boolean attention = tbNtMianHunanService.attention(param);
        //获取招标原文
        String content = tbNtMianHunanService.queryBidsDetailsCentendString(param);
        // type = 1  招标详情   ||  type = 2  中标详情
        String type = MapUtils.getString(param, "type");
        if(type.equals("1") && !"".equals(type)){
            //获取招标详情
            Map<String, Object> map = tbNtMianHunanService.queryTendersNociteDetails(param);
            map.put("content",content);
            //map1.put("clickCount",count);
            map.put("collected",attention);
            resultMap.put("clickCount",count);
            seccussMap(resultMap,map);
            return resultMap;
        }
        Map<String, Object> map = tbNtMianHunanService.queryBidsNociteDetails(param);
        map.put("content",content);
        //map1.put("clickCount",count);
        map.put("collected",attention);
        resultMap.put("clickCount",count);
        seccussMap(resultMap,map);
        return resultMap;
    }
}
