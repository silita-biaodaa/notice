package com.silita.notice.web.impl;

import com.github.pagehelper.PageInfo;
import com.silita.notice.service.CommonService;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.utils.PropertyUtil;
import com.silita.notice.utils.RedisShardedPoolUtil;
import com.silita.notice.web.BaseCastTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeServiceImplTest extends BaseCastTest {
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;
    @Autowired
    private  Environment environment;
    @Autowired
    private CommonService sysAreaService;



    @Test
    public  void test(){
        //System.out.println(environment.getProperty("redis1.ip"));
        //System.out.println(PropertyUtil.getProperty("redis1.ip"));
        //System.out.println("获取的值："+RedisShardedPoolUtil.get("inter_company_list_-2128054673"));
        //System.out.println(PropertyUtil.getProperty("redis1.ip"))
        /*String key = "filter_company";
        System.out.println("获取的值："+RedisShardedPoolUtil.get(key));*/
        String key = "filter_company";
        RedisShardedPoolUtil.del(key);


    }

    @Test
    public void zhongbiao(){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("pageNo",1);
        param.put("pageSize",20);
        param.put("type",2);
        param.put("regions","hunan");
        PageInfo pageInfo = tbNtMianHunanService.queryBids(param);
        System.out.println(pageInfo);
    }

    @Test
    public void zhongbiaoxiangqing(){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("type","2");
        param.put("userId","");
        param.put("id","ac22eaca7cba4bd795316db41ff61c4c");
        param.put("source","hunan");
        param.put("provice","hunan");
        param.put("city","xiangtan");
        Map<String, Object> map = tbNtMianHunanService.queryBidsNociteDetails(param);
        System.out.println(map);
    }

    @Test
    public void zhongbiaotest(){

    }

    @Test
    public void getArea(){
        List<Map<String, Object>> area = sysAreaService.getArea();

        for (Map<String, Object> map : area) {
            System.out.println(map);
        }
    }

    @Test
    public void getQual(){
       /* Map<String,Object> param = new HashMap<>();
        List<Map<String, Object>> list = sysAreaService.queryCompanyQual(param);
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }*/


    }

    @Test
    public void getpbMode(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> list = sysAreaService.queryPbModes(map);
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }

    }

    @Test
    public void getType(){
        List<Map<String, Object>> type = sysAreaService.type();
        for (Map<String, Object> map : type) {
            System.out.println(map);
        }

    }

    @Test
    public void qx(){
        sysAreaService.updRelatedId();
    }


}
