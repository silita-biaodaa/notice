package com.silita.notice.web;

import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.service.CompanyService;
import com.silita.notice.service.TbNtMianHunanService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/newnocite")
public class NociteController extends BaseController {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(NociteController.class);
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;

    @Autowired
    private CompanyService companyService;

    /**
     * 查询公告  -  中标
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zhongbiao/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map queryBids(@RequestBody Map<String, Object> param) {
        logger.info("-------------------进入该方法:zhongbiao----------------------");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //分页限制 最多到30页  每页20条记录
            checkPage(param);
            PageInfo pageInfo = tbNtMianHunanService.queryBids(param);
            logger.info("-------------------查询成功----------------------");
            seccussMap(resultMap, pageInfo);
        } catch (NullPointerException e) {
            errorMsg(resultMap, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 前端：精确企业名称
     * 后台：通过获取企业名称 模糊匹配中标的公告
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/company/zhongbiao/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map queryCompanyName(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //分页限制 最多到30页  每页20条记录
            checkPage(param);
            PageInfo pageInfo = tbNtMianHunanService.queryCompanyName(param);
            seccussMap(resultMap, pageInfo);
        } catch (Exception e) {
            logger.info("企业中标", e);
            errorMsg(resultMap, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 查询招标信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zhaobiao/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map queryTenders(@RequestBody Map<String, Object> param) {
        logger.info("-------------------进入该方法:zhaobiao----------------------");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            checkPage(param);
            PageInfo pageInfo = tbNtMianHunanService.queryTenders(param);
            logger.info("-------------------查询成功----------------------");
            seccussMap(resultMap, pageInfo);
        } catch (NullPointerException e) {
            errorMsg(resultMap, e.getMessage());
        }
        return resultMap;
    }

    /*
     *公告详情
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/nociteDetails/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map queryNociteDetails(@PathVariable String id, @RequestBody Map<String, Object> param){
        param.put("id", id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> proviceCity = tbNtMianHunanService.queryProviceCity(param);
        String provice = "";
        String city = "";
        String snatchId = "";
        if (proviceCity != null && proviceCity.size() > 0) {
            provice = (String) proviceCity.get("provice");
            city = (String) proviceCity.get("city");
            snatchId = (String) proviceCity.get("snatchId");
            if (StringUtils.isNotEmpty(snatchId)) {
                param.put("snatchId", snatchId);
            }
            if (StringUtils.isNotEmpty(provice)) {
                param.put("provice", provice);
            }
            if (StringUtils.isNotEmpty(city)) {
                param.put("city", city);
            }
        } else {
            param.put("snatchId", "");
            param.put("provice", "");
            param.put("city", "");
        }


        //获取省级名称
        String proviceCode = "";
        if (StringUtils.isNotEmpty(provice)) {
            Map<String, Object> proviceName = tbNtMianHunanService.queryProviceName(param);
            System.out.println(proviceName);
            if (proviceName != null && proviceName.size() > 0) {
                //获取省级名称
                proviceCode = (String) proviceName.get("proviceCode");
            }
        }
        //获取市级名称
        String cityCode = "";
        if (StringUtils.isNotEmpty(city)) {
            Map<String, Object> cityName = tbNtMianHunanService.queryCityName(param);
            if (null != cityName && cityName.size() > 0) {
                //获取市级名称
                cityCode = (String) cityName.get("cityCode");
            }
        }
        //获取userId
        String userId = VisitInfoHolder.getUserId();
        param.put("userId", userId);
        //获取点击量
        Integer count = tbNtMianHunanService.count(param);
        //获取是否关注
        Boolean attention = tbNtMianHunanService.attention(param);
        //获取招标原文
        String content = "";
        if (StringUtils.isNotEmpty(snatchId)) {
            try {
                content = tbNtMianHunanService.queryBidsDetailsCentendString(param);
            }catch (Exception e){
                errorMsg(resultMap, e.getMessage());
            }

        }
        // type = 1  招标详情   ||  type = 2  中标详情
        String type = MapUtils.getString(param, "type");
        if (type.equals("1") && !"".equals(type)) {
            try {
                //获取招标详情
                Map<String, Object> map = tbNtMianHunanService.queryTendersNociteDetails(param);
                if (null != map && map.size() >0) {
                    if (StringUtils.isNotEmpty(content)) {
                        map.put("content", content);
                    }
                    map.put("projDq", proviceCode + "-" + cityCode);
                    map.put("collected", attention);
                    resultMap.put("clickCount", count);
                    Integer relCompanySize = companyService.relCompanySize(param);
                    resultMap.put("relCompanySize", relCompanySize);
                    seccussMap(resultMap, map);
                }else{
                    seccussMap(resultMap, map);
                }

            } catch (Exception e) {
                errorMsg(resultMap, e.getMessage());
            }
            return resultMap;
        }
        try {
            Map<String, Object> map = tbNtMianHunanService.queryBidsNociteDetails(param);
            if (StringUtils.isNotEmpty(content)) {
                map.put("content", content);
            }
            map.put("collected", attention);
            resultMap.put("clickCount", count);
            seccussMap(resultMap, map);
        } catch (Exception e) {
            logger.error("查询详情失败", e);
            errorMsg(resultMap, "查询详情失败!");
        }
        return resultMap;
    }
}
