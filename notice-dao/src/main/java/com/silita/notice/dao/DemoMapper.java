package com.silita.notice.dao;


import com.silita.notice.model.Demo;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DemoMapper extends MySqlMapper<Demo> {

    Demo getDemo(Integer id);
}
