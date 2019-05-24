package com.silita.notice.web;

import com.silita.notice.model.Demo;
import com.silita.notice.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: notice
 * @description: demo
 * @author: zhangxiahui
 * @create: 2019-05-23 20:12
 **/
@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;

    @ResponseBody  // 返回 Json 数据
    @GetMapping("/getDemo")
    private Demo getDemo(){
        return demoService.getDemo(1); // 成功返回
    }

}
