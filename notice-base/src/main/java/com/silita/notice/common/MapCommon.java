package com.silita.notice.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapCommon {
    public static List<Map<String,Object>> getNoticeType() {
        List<Map<String, Object>> typeList = new ArrayList<>();
        Map<String, Object> typeMap = new HashMap<>();
        //01:施工  02:监理  03:设计  04:勘察  05:采购  06:其他
        typeMap.put("name", "施工");
        typeMap.put("projectType", "01");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "监理");
        typeMap.put("projectType", "02");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "设计");
        typeMap.put("projectType", "03");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "勘察");
        typeMap.put("projectType", "04");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "采购");
        typeMap.put("projectType", "05");
        typeList.add(typeMap);
        typeMap = new HashMap<>();
        typeMap.put("name", "其他");
        typeMap.put("projectType", "06");
        typeList.add(typeMap);
        return typeList;
    }
}
