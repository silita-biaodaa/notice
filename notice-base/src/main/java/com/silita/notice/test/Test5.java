package com.silita.notice.test;

import org.apache.commons.collections.map.HashedMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test5 {
    public static void main(String[] args) {

        List<Map<String,Object>> list = new  ArrayList<Map<String,Object>>();



  /*      String a = "a/1,b/2,c/3";
        String[] split = a.split("\\,");
        for (String s : split) {

            Map<String,Object> map = new HashMap<String,Object>();
            String[] split1 = s.split("\\/");
            if(split1.length>=2){

                map.put("quaCode",split1[0]);
                map.put("gradeCode",split1[1]);


            }else{
                map.put("quaCode",split1[0]);
                //map.put("gradeCode",split1[1]);

            }
            list.add(map);


        }
        for (Map<String, Object> map2 : list) {
            System.out.println(map2);
        }*/


/*
        Object quaCode = map.get("quaCode");
        System.out.println(quaCode);

        for (String s : map.keySet()) {
            System.out.println(s);
        }*/








    }
}
