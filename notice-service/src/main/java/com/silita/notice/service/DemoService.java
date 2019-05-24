package com.silita.notice.service;

import com.silita.notice.dao.DemoMapper;
import com.silita.notice.model.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: notice
 * @description: demo的service类
 * @author: zhangxiahui
 * @create: 2019-05-23 20:08
 **/
@Service("demoService")
public class DemoService {

    @Autowired
    DemoMapper demoMapper;


    public Demo getDemo(Integer id){
        return demoMapper.getDemo(id);

    }
}
