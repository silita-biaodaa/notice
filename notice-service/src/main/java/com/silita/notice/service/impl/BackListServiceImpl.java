package com.silita.notice.service.impl;

import com.silita.notice.dao.TbBackListMapper;
import com.silita.notice.service.BackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhushuai on 2019/12/30.
 */
@Service
public class BackListServiceImpl implements BackListService {

    @Autowired
    private TbBackListMapper tbBackListMapper;

    @Override
    public List<String> getBackList() {
        return tbBackListMapper.queryBackList();
    }
}
