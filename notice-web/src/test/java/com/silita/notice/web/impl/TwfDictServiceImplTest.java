package com.silita.notice.web.impl;

import com.silita.notice.NoticeWebApplication;
import com.silita.notice.model.TwfDict;
import com.silita.notice.service.TwfDictService;
import com.silita.notice.web.BaseCastTest;
import com.silita.notice.web.NoticeWebApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


public class TwfDictServiceImplTest extends BaseCastTest {
    @Autowired
    private TwfDictService twfDictService;

    private TwfDict twfDict = new TwfDict();

    @Test
    public void addData() {
        Map<String,Object> param = new HashMap<>();
        //String s = "建筑工程,公路工程,铁路工程,港口与航道工程,水利水电工程,电力工程,矿山工程,冶金工程,石油化工工程,市政公用工程,通信工程,电信工程,地基基础工程,建筑装修装饰工程,建筑幕墙工程,预拌混凝土,钢结构工程,消防设施工程,模板脚手架,起重设备安装,防水防腐保温工程,建筑机电安装工程,古建筑工程,城市及道路照明工程,环保工程, 桥梁工程,电子与智能化工程,隧道工程,公路路面工程,公路路基工程,铁路电务工程,铁路铺轨架梁工程,铁路电气化工程,机场场道工程,民航空管工程及机场弱电系统工程,港口与海岸工程,港航设备安装及水上交管工程,航道工程,水利水电机电设备安装工程,水工金属结构制作与安装工程,河湖整治工程,通航建筑工程,核工程,输变电工程,公路交通工程,海洋石油工程,特种工程,勘察项目,设计项目,监理项目,招标代理工程,地质灾害治理工程,城市园林绿化工程,土石方工程,文物保护工程,设计与施工一体化项目,造价咨询项目,公路养护工程,检测项目,土整项目,室内装饰,EPC项目,PPP项目,安防工程";
        //String s = "施工,监理,设计,勘察,采购,其他,PPP,设计施工,EPC,检测,施工采购,造价咨询,招标代理";
        //String s = "磋商公告,补充公告,答疑公告,流标公告,澄清公告,延期公告,变更/更正/更改公告,废标公告,终止公告,修改公告,招标控制价,资审结果,资格预审,入围公告,暂停公告";
        //String s = "法定代表人,项目负责人,授权委托人,法定代表人和项目负责人,法定代表人和授权委托人,法定代表人或项目负责人,法定代表人或授权委托人,项目负责人和授权委托人,项目负责人或授权委托人";
        String s = "外省入湘备案,长沙市公共资源交易中心备案,株洲市公共资源交易中心备案,湘潭市公共资源交易中心备案,张家界市公共资源交易中心备案,湘西公共资源交易中心备案";
        //String s = "招标公告,中标公告";
        //String s = "未开标,流标,重新招标,终止,中止,废标,延期,已开标";
        param.put("s",s);
        String type = "6";
        param.put("type",type);
        twfDictService.addData(param);


    }


}