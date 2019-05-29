package com.silita.notice.dao;


import com.silita.notice.model.Demo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;
@Repository
public interface DemoMapper extends MySqlMapper<Demo> {

    Demo getDemo(Integer id);
}
