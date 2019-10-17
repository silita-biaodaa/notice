package com.silita.notice.web;

import com.github.pagehelper.PageInfo;
import com.silita.notice.base.BaseController;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    /**
     * 查询符合该公告资质的企业
     *
     * @param param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/qual/list/{id}", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map<String, Object> list(HttpServletRequest request, @PathVariable String id, @RequestBody Map<String, Object> param) {
        param.put("id", id);
        Map<String, Object> resultMap = new HashMap<>();
        checkPage(param);
        param.put("userId", VisitInfoHolder.getUserId(request));
        PageInfo pageInfo = companyService.queryQualCom(param);
        seccussMap(resultMap, pageInfo);
        return resultMap;
    }


}
