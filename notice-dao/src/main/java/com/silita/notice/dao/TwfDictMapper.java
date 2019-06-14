package com.silita.notice.dao;

import com.silita.notice.model.TwfDict;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface TwfDictMapper {

    void addData(Map<String,Object> param);
}