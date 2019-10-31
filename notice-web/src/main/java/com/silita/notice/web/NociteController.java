package com.silita.notice.web;

import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.service.CompanyService;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.service.impl.ElasticsearchService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/newnocite")
public class NociteController extends BaseController {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(NociteController.class);
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    ElasticsearchService elasticsearchService;

    /**
     * 查询公告  -  中标
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/zhongbiao/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map queryBids(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        logger.info("-------------------进入该方法:zhongbiao----------------------");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //获取userId
            String userId = VisitInfoHolder.getUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
                param.put("userId", userId);
            }
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
    public Map queryCompanyName(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //获取userId
            String userId = VisitInfoHolder.getUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
                param.put("userId", userId);
            }
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
    public Map queryTenders(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        logger.info("-------------------进入该方法:zhaobiao----------------------");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //获取userId
            String userId = VisitInfoHolder.getUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
                param.put("userId", userId);
            }
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
    public Map queryNociteDetails(HttpServletRequest request, @PathVariable String id, @RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        param.put("id", id);
        Map<String, Object> proviceCity = tbNtMianHunanService.queryProviceCity(param);//查省级编号和市级编号和爬取id
        String snatchId = "";
        if (null != proviceCity.get("snatchId")) {
            snatchId = MapUtils.getString(proviceCity, "snatchId");
        }
        param.put("snatchId", snatchId);
        try {
            Map<String, Object> map;
            // type = 1  招标详情   ||  type = 2  中标详情
            String type = MapUtils.getString(param, "type");
            if (type.equals("1") && !"".equals(type)) {
                //获取招标详情
                map = tbNtMianHunanService.queryTendersNociteDetails(param);
                if (MapUtils.isEmpty(map)) {
                    seccussMap(resultMap, map);
                    return resultMap;
                }
                //获取省市级名称
                if (null != proviceCity && proviceCity.size() > 0) {
                    if (StringUtils.isNotEmpty(MapUtils.getString(proviceCity, "provice")) && StringUtils.isNotEmpty(MapUtils.getString(proviceCity, "city"))) {
                        map.put("projDq", MapUtils.getString(proviceCity, "provice") + "-" + MapUtils.getString(proviceCity, "city"));
                    } else if (StringUtils.isNotEmpty(MapUtils.getString(proviceCity, "provice"))) {
                        map.put("projDq", MapUtils.getString(proviceCity, "provice"));
                    }
                }
                resultMap.put("relCompanySize", companyService.relCompanySize(param));
            } else {
                map = tbNtMianHunanService.queryBidsNociteDetails(param);
            }
            //获取userId
            param.put("userId", VisitInfoHolder.getUserId(request));
            //获取点击量
            Integer count = tbNtMianHunanService.count(param);
            //获取是否关注
            Boolean attention = tbNtMianHunanService.attention(param);
            //获取招标原文
            String content = "";
            if (StringUtils.isNotEmpty(snatchId)) {
                content = tbNtMianHunanService.queryBidsDetailsCentendString(param);
            }
            map.put("content", content);
            map.put("collected", attention);
            resultMap.put("clickCount", count);
            seccussMap(resultMap, map);
        } catch (Exception e) {
            logger.error("查询详情失败", e);
            errorMsg(resultMap, e.getMessage());
        }
        return resultMap;
    }


    /**
     * 查询相关公告
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/correlation/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map listCorrelation(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            seccussMap(resultMap, tbNtMianHunanService.listNoticeCorrelation(param));
        } catch (Exception e) {
            errorMsg(resultMap, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 查询某一日期的爬取id
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/count/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map listNoticeCount(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<String> list = tbNtMianHunanService.getNoticeCountList(param);
            seccussMap(resultMap, list);
            return resultMap;
        } catch (Exception e) {
            logger.error("查询失败！", e);
            errorMsg(resultMap, e.getMessage());
            return resultMap;
        }
    }

    /**
     * 查询订阅结果页
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/subscribe/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map listsubscribe(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            param.put("userId", VisitInfoHolder.getUserId(request));
            resultMap = elasticsearchService.getSubscribe(param, "show");
            seccussMap(resultMap);
            return resultMap;
        } catch (Exception e) {
            logger.error("查询失败！", e);
            errorMsg(resultMap, e.getMessage());
            return resultMap;
        }
    }

    /**
     * 公告已读
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/read", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map read(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>(2);
        try {
            param.put("userId", VisitInfoHolder.getUserId(request));
            tbNtMianHunanService.setNoticeReadStatus(param);
            seccussMap(resultMap);
            return resultMap;
        } catch (Exception e) {
            logger.error("设置失败！", e);
            errorMsg(resultMap, e.getMessage());
            return resultMap;
        }
    }

    /**
     * es导入
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/es/import", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map esImport() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            tbNtMianHunanService.importNoticeForEs();
            seccussMap(resultMap);
            return resultMap;
        } catch (Exception e) {
            logger.error("插入失败！", e);
            errorMsg(resultMap, e.getMessage());
            return resultMap;
        }
    }

}
