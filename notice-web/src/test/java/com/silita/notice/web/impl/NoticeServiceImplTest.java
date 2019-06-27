package com.silita.notice.web.impl;

import com.github.pagehelper.PageInfo;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.utils.PropertyUtil;
import com.silita.notice.utils.PropertyUtils;
import com.silita.notice.utils.RedisShardedPoolUtil;
import com.silita.notice.web.BaseCastTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class NoticeServiceImplTest extends BaseCastTest {
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;
    @Autowired
    private  Environment environment;

    @Test
    public  void test(){
        //System.out.println(environment.getProperty("redis1.ip"));
        //System.out.println(PropertyUtil.getProperty("redis1.ip"));
        //System.out.println("获取的值："+RedisShardedPoolUtil.get("inter_company_list_-2128054673"));
        System.out.println(PropertyUtil.getProperty("redis1.ip"));

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
}
