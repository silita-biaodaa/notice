package com.silita.notice.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.model.TbNtMianHunan;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.service.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/nocite")
public class NociteController extends BaseController {
    @Autowired
    //private NociteMianService nociteMianService;

    private TbNtMianHunanService tbNtMianHunanService;

    /**
     * 查询公告  -  中标
     * @param nociteMian
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryBids")
    public Map queryBids(TbNtMianHunan nociteMian){
        nociteMian.setProviceCode("hunan");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        PageHelper.startPage(1,20);
        List<Map<String, Object>> maps = tbNtMianHunanService.queryBids(nociteMian);
        PageInfo pageInfo = new PageInfo(maps);
        seccussMap(resultMap,pageInfo);
        return resultMap;
    }

    /**
     *
     * 通过企业名称 模糊查询中标的公告
     * @param nociteMian
     * @param httpServletRequest
     * @param CompanyName  企业名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCompanyName")
    public Map queryCompanyName(TbNtMianHunan nociteMian,HttpServletRequest httpServletRequest,String CompanyName){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<String> list = tbNtMianHunanService.queryCompanyName(CompanyName);
        PageInfo pageInfo = new PageInfo(list);
        seccussMap(resultMap,pageInfo);
        return resultMap;
    }
    @ResponseBody
    @RequestMapping("/queryTenders")
    public Map queryTenders(){
        Map<String,Object> resultMap = new HashMap<String,Object>();

        return resultMap;
    }



}
