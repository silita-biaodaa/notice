package com.silita.notice.web;

import com.silita.notice.base.BaseController;
import com.silita.notice.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/newcommon")
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;


    /**
     * 获取筛选
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/filter",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map  filter(){
        Map<String,Object> param = new HashMap<>();
        Map<String,Object> param1 = new HashMap<>();
        Map<String,Object> param2 = new HashMap<>();
        param1.put("zzType","1");
        param2.put("zzType","2");


        Map<String,Object> resultMap = new HashMap<>();



        Map<String,Object> map = new HashMap<>();

        List<Map<String, Object>> area = commonService.getArea();
        List<Map<String, Object>> type = commonService.type();
        List<Map<String, Object>> pbMode = commonService.queryPbModes(param);
        List<Map<String, Object>> comQual = commonService.queryCompanyQual(param2);
        List<Map<String, Object>> nocitequal = commonService.queryCompanyQual(param1);



        map.put("area",area);
        map.put("type",type);
        map.put("pbMode",pbMode);
        map.put("comQual",comQual);
        map.put("nociteQual",nocitequal);

        seccussMap(resultMap,map);
        return map;
    }

}
