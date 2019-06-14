package com.silita.notice;

import com.silita.notice.utils.PinYinUtil;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        String s = "建筑工程,公路工程,铁路工程,港口与航道工程,水利水电工程,电力工程,矿山工程,冶金工程,石油化工工程,市政公用工程,通信工程,电信工程,地基基础工程,建筑装修装饰工程,建筑幕墙工程,预拌混凝土,钢结构工程,消防设施工程,模板脚手架,起重设备安装,防水防腐保温工程,建筑机电安装工程,古建筑工程,城市及道路照明工程,环保工程, 桥梁工程,电子与智能化工程,隧道工程,公路路面工程,公路路基工程,铁路电务工程,铁路铺轨架梁工程,铁路电气化工程,机场场道工程,民航空管工程及机场弱电系统工程,港口与海岸工程,港航设备安装及水上交管工程,航道工程,水利水电机电设备安装工程,水工金属结构制作与安装工程,河湖整治工程,通航建筑工程,核工程,输变电工程,公路交通工程,海洋石油工程,特种工程,勘察项目,设计项目,监理项目,招标代理工程,地质灾害治理工程,城市园林绿化工程,土石方工程,文物保护工程,设计与施工一体化项目,造价咨询项目,公路养护工程,检测项目,土整项目,室内装饰,EPC项目,PPP项目,安防工程";
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();

    /*    PinYinUtil pinYinUtil = new PinYinUtil();
        String[] split = s.split("\\,");
        List<String> list1 = Arrays.asList(split);


        for (String s2 : list1) {
            String pinyin = pinYinUtil.cn2py(s2);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("pinyin",pinyin);
            maps.put("name",s2);

           listmap.add(maps);
        }



        for (Map<String, Object> map : listmap) {
            System.out.println(map);
        }*/

    }
}
