package com.silita.notice.service;

import com.silita.notice.model.TbNtMianHunan;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TbNtMianHunanServiceimplTest extends BaseCastTest{

    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;

    private TbNtMianHunan tbNtMianHunan;

    @Test
    public void queryBids() {

        tbNtMianHunan.setProviceCode("hunan");

        List<Map<String, Object>> list = tbNtMianHunanService.queryBids(tbNtMianHunan);

        for (Map<String, Object> map : list) {
            System.out.println(map.toString());
        }
    }

    @Test
    public void queryCompanyName() {
    }
}