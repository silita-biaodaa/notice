package com.silita.notice.service;

import com.silita.notice.model.TwfDict;
import org.springframework.stereotype.Repository;

import java.util.Map;


public interface TwfDictService {

    void addData(Map<String,Object> param);
}