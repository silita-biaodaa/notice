package com.silita.notice.web;

import com.silita.notice.base.BaseController;
import com.silita.notice.service.CommonService;
import com.silita.notice.utils.RedisShardedPoolUtil;
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
@RequestMapping("/new/common")
public class CommonController extends BaseController {
    @Autowired
    private CommonService commonService;

    /**
     * 获取筛选
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/condition", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map conditionFilter() {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String key = "filter_company";
        Object obj = RedisShardedPoolUtil.get(key);
        if (null != obj) {
            map = (Map<String, Object>) obj;
            seccussMap(resultMap, map);
            return resultMap;
        }
        Map<String, Object> notice = new HashMap<>();
        notice.put("bizType", "1");
        Map<String, Object> com = new HashMap<>();
        com.put("bizType", "2");
        List<Map<String, Object>> area = commonService.getArea();
        List<Map<String, Object>> type = commonService.type();
        List<Map<String, Object>> pbMode = commonService.queryPbModes(param);
        List<Map<String, Object>> noticeList = commonService.queryQua(notice);
        List<Map<String, Object>> comList = commonService.queryQua(com);
        map.put("area", area);
        map.put("type", type);
        map.put("pbMode", pbMode);
        map.put("noticeQua", noticeList);
        map.put("comQua", comList);
        RedisShardedPoolUtil.set(key, map);
        seccussMap(resultMap, map);
        return resultMap;
    }


    /**
     * 获取筛选
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/filter/qual", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Map conditionFilterQual(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        super.seccussMap(resultMap, commonService.getFilterQual(param));
        return resultMap;
    }


}
