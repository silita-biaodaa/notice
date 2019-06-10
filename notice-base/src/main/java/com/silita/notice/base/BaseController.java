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

    public String CODE_FLAG = "code";

    public String MSG_FLAG = "msg";

    public String FAIL_CODE = Constant.FAIL_CODE;

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

    protected void errorMsg(Map resultMap, String errMsg) {
        resultMap.put(this.CODE_FLAG, this.FAIL_CODE);
        resultMap.put(this.MSG_FLAG, errMsg);
    }

    public void checkPage(Map<String,Object> param){
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        if(pageNo > 30){
            param.put("pageNo",30);
        }
        if (pageSize > 20){
            param.put("pageSize",20);
        }
    }


}
