package com.silita.notice.web.impl;

import com.github.pagehelper.PageInfo;
import com.silita.notice.common.RegionCommon;
import com.silita.notice.dao.SnatchurlMapper;
import com.silita.notice.dao.TbCommentInfoMapper;
import com.silita.notice.dao.TbNtMianHunanMapper;
import com.silita.notice.service.CommonService;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.utils.PropertyUtil;
import com.silita.notice.utils.RedisShardedPoolUtil;
import com.silita.notice.web.BaseCastTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoticeServiceImplTest extends BaseCastTest {
    @Autowired
    private TbNtMianHunanService tbNtMianHunanService;
    @Autowired
    private Environment environment;
    @Autowired
    private CommonService sysAreaService;

    @Autowired
    private TbCommentInfoMapper tbCommentInfoMapper;
    @Autowired
    private SnatchurlMapper snatchurlMapper;
    @Autowired
    private TbNtMianHunanMapper tbNtMianHunanMapper;


    @Test
    public void test() {
        //System.out.println(environment.getProperty("redis1.ip"));
        //System.out.println(PropertyUtil.getProperty("redis1.ip"));
        //System.out.println("获取的值："+RedisShardedPoolUtil.get("inter_company_list_-2128054673"));
        //System.out.println(PropertyUtil.getProperty("redis1.ip"))
        /*String key = "filter_company";
        System.out.println("获取的值："+RedisShardedPoolUtil.get(key));*/
        String key = "filter_company";
        RedisShardedPoolUtil.del(key);


    }

    @Test
    public void zhongbiao() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageNo", 1);
        param.put("pageSize", 20);
        param.put("type", 2);
        param.put("regions", "hunan");
        PageInfo pageInfo = tbNtMianHunanService.queryBids(param);
        System.out.println(pageInfo);
    }

    @Test
    public void zhongbiaoxiangqing() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", "2");
        param.put("userId", "");
        param.put("id", "ac22eaca7cba4bd795316db41ff61c4c");
        param.put("source", "hunan");
        param.put("provice", "hunan");
        param.put("city", "xiangtan");
        Map<String, Object> map = tbNtMianHunanService.queryBidsNociteDetails(param);
        System.out.println(map);
    }

    @Test
    public void zhongbiaotest() {

    }

    @Test
    public void getArea() {
        List<Map<String, Object>> area = sysAreaService.getArea();

        for (Map<String, Object> map : area) {
            System.out.println(map);
        }
    }

    @Test
    public void getQual() {
       /* Map<String,Object> param = new HashMap<>();
        List<Map<String, Object>> list = sysAreaService.queryCompanyQual(param);
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }*/


    }

    @Test
    public void getpbMode() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = sysAreaService.queryPbModes(map);
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }

    }

    @Test
    public void getType() {
        List<Map<String, Object>> type = sysAreaService.type();
        for (Map<String, Object> map : type) {
            System.out.println(map);
        }

    }

    @Test
    public void qx() {
        //sysAreaService.updRelatedId();
    }

    @Test
    public void qx2() {
  /*      List<Map<String, Object>> list = tbCommentInfoMapper.queryRelatedId();
        Map<String,Object> param = new HashMap<>();
        for (Map<String, Object> map : list) {
            param.put("relatedId",map.get("relatedId"));
            param.put("pkid",map.get("pkid"));

            List<String> titleList = snatchurlMapper.queryTitle(param);
            for (String s : titleList) {
                param.put("title",s);
                List<String> listPkid = tbNtMianHunanMapper.queryPkid(param);
                for (String s1 : listPkid) {
                    param.put("relatedId",s1);
                    tbCommentInfoMapper.updateRelatedId(param);
                }

            }

        }*/


        List<Map<String, Object>> list = tbCommentInfoMapper.queryRelatedId2();
        Map<String, Object> param = new HashMap<>();
        for (Map<String, Object> map : list) {
            param.put("relatedId", map.get("relatedId"));
            param.put("pkid", map.get("pkid"));

            List<String> titleList = snatchurlMapper.queryTitle(param);
            for (String s : titleList) {
                param.put("title", s);
                List<String> listPkid = tbNtMianHunanMapper.queryPkid(param);
                for (String s1 : listPkid) {
                    param.put("relatedId", s1);
                    tbCommentInfoMapper.updateRelatedId2(param);
                }

            }

        }
    }

    @Test
    public  void query() {

        String phones = "0734-8236666";
        System.out.println("ssss1:"+phones.substring(0, 4));
        System.out.println("ssss2:"+phones.substring(5));

        String substring = phones.substring(0, 4);
        String substring1 = phones.substring(5);
        String r = substring+substring1;


        String phone = phones.trim();
        System.out.println(phone);
        String[] split = phone.split(";");
        String s1 = split[0].toString();
        String s2 = r.replaceAll("(\\d{4})\\d{4}(\\d{3})", "$1****$2");

        String substring2 = s2.substring(0, 4);
        String substring3 = s2.substring(4);
        System.out.println(substring2+"-"+substring3);
        System.out.println("s2:"+s2);


        Pattern patternPhone = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0-9])|(18[0,5-9]))\\d{8}");

        Pattern patternFixed = Pattern.compile("(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)");
        // 创建匹配给定输入与此模式的匹配器。
        Matcher matcherPhone = patternPhone.matcher(phones);


        Matcher matcherFixed = patternFixed.matcher(phones);


        String a = "";
        String b = "";



        //查找字符串中是否有符合的子字符串
        while(matcherPhone.find()){
            a=matcherPhone.group()+";"+a;


        }

        //查找字符串中是否有符合的子字符串
        while(matcherFixed.find()){
            b=matcherFixed.group()+";"+b;

        }

        System.out.println("a+b:"+a+b);




    }

    @Test
    public void quhanzi(){
     /*   Map<String, Object> region = RegionCommon.region();
        for (String s : region.keySet()) {
            System.out.println(s);
        }*/
    }


}
