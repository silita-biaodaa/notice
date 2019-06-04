package com.silita.notice.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.Constant;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * 公共类
 */
public class BaseController {

    public void seccussMap(Map resultMap,Object data){
        resultMap.put("code",Constant.SUCCESS_CODE);
        resultMap.put("msg",Constant.SUCCESS_MSG);
        resultMap.put("data",data);
    }

    public void seccussMap(Map resultMap, PageInfo pageInfo) {
        resultMap.put("code",Constant.SUCCESS_CODE);
        resultMap.put("msg",Constant.SUCCESS_MSG);
        if (pageInfo != null) {
            resultMap.put("list", pageInfo.getList());
            resultMap.put("pageNo", pageInfo.getPageNum());
            resultMap.put("pageSize", pageInfo.getPageSize());
            resultMap.put("total", pageInfo.getTotal());
            resultMap.put("pages", pageInfo.getPages());
        }
    }

    public void pdpageNo(Map<String,Object> param){
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        if (pageNo <= 30){
            PageHelper.startPage(pageNo,pageSize);
        }else if(pageNo > 30){
            PageHelper.startPage(30,20);
        }
    }


}
