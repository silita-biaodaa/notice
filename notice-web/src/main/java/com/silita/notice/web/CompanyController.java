package com.silita.notice.web;

import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    /**
     * 查询符合该公告资质的企业
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qual/list",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String,Object> queryCom(@RequestBody Map<String,Object> param){
        Map<String,Object> resultMap = new HashMap<>();
        checkPage(param);
        PageInfo pageInfo = companyService.queryCom(param);
        seccussMap(resultMap,pageInfo);
        return resultMap;
    }

}
