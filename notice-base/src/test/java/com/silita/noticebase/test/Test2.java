package com.silita.noticebase.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();

        Map<String,Object> map = new HashMap<String,Object>();





        map.put("name","北京市");
        map.put("name","天津市");
        map.put("name","河北省");
        map.put("name","山西省");
        map.put("name","内蒙古自治区");
        map.put("name","辽宁省");
        map.put("name","吉林省");
        map.put("name","黑龙江省");
        map.put("name","上海市");
        map.put("name","江苏省");
        map.put("name","浙江省");
        map.put("name","浙江省");
        map.put("name","福建省");
        map.put("name","江西省");
        map.put("name","山东省");
        map.put("name","河南省");
        map.put("name","湖北省");
        map.put("name","广东省");
        map.put("name","广西壮族自治区");
        map.put("name","海南省");
        map.put("name","重庆市");
        map.put("name","四川省");
        map.put("name","贵州省");
        map.put("name","云南省");
        map.put("name","陕西省");
        map.put("name","甘肃省");
        map.put("name","青海省");
        map.put("name","宁夏回族自治区");
        map.put("name","湖南省");
        map.put("name","新疆维吾尔自治区");
        map.put("name","西藏自治区");



        map.put("code","beij");
        map.put("code","tianj");
        map.put("code","hebei");
        map.put("code","sanx");
        map.put("code","neimg");
        map.put("code","liaon");
        map.put("code","jil");
        map.put("code","c");
        map.put("code","shangh");
        map.put("code","jiangs");
        map.put("code","zhej");
        map.put("code","anh");
        map.put("code","fuj");
        map.put("code","jiangx");
        map.put("code","shand");
        map.put("code","henan");
        map.put("code","hubei");
        map.put("code","guangd");
        map.put("code","guangx");
        map.put("code","hain");
        map.put("code","chongq");
        map.put("code","sichuan");
        map.put("code","guiz");
        map.put("code","yunn");
        map.put("code","shanxi");
        map.put("code","gans");
        map.put("code","qingh");
        map.put("code","ningx");
        map.put("code","hunan");
        map.put("code","xinjiang");
        map.put("code","xizang");







        lists.add(map);

        for (String s : map.keySet()) {
            System.out.println(s);
        }



    }
}
