package com.silita.notice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.*;
import com.silita.notice.service.CompanyService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private TbNtRegexQuaMapper tbNtRegexQuaMapper;
    @Autowired
    private TbCompanyMapper tbCompanyMapper;

    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private ColleCompanyNewMapper colleCompanyNewMapper;


    @Override
    public PageInfo queryCom(Map<String, Object> param) {
        List<Map<String, Object>> comListMap = new ArrayList<>();
        String noticeId = MapUtils.getString(param, "id");
        String quaRegex = tbNtRegexQuaMapper.queryNoticeId(noticeId);
        param.put("quaRegex", quaRegex);
        List<Map<String, Object>> comList = tbCompanyMapper.queryQualCom(param);
        PageInfo pageInfo = new PageInfo(comList);
        return pageInfo;
    }

    /**
     * 获取符合该公告资质企业的数量
     *
     * @param param
     * @return
     */
    @Override
    public Integer relCompanySize(Map<String, Object> param) {
        Integer count = 0;
        //获取省份的name
        String name = sysAreaMapper.queryName(param);
        param.put("regisAddress", name);
        //获取资质关系表达式的列quaRegex
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);
        if(list.size() >= 0 || null != list){
            for (String s1 : list) {
                param.put("quaRegex", s1);
                //获取符合数量
                Integer relCompanySize = tbCompanyMapper.queryRelCompanySize(param);
                count = count + relCompanySize;
            }
        }


        return count;
    }

    /**
     * 获取符合该公告资质的企业
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo queryQualCom(Map<String, Object> param) {
        String name = sysAreaMapper.queryName(param);
        param.put("regisAddress", name);
        List<String> list = tbNtRegexQuaMapper.queryQuaRegex(param);

        param.put("list", list);
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        PageHelper.startPage(pageNo, pageSize);


        List<Map<String, Object>> listMap = tbCompanyMapper.queryQualCom(param);
        for (Map<String, Object> map : listMap) {
            String phones =(String) map.get("phone");
            Pattern patternPhone = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
            Pattern patternFixed = Pattern.compile("(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)");
            // 创建匹配给定输入与此模式的匹配器。
            Matcher matcherPhone = patternPhone.matcher(phones);
            Matcher matcherFixed = patternFixed.matcher(phones);

            String a = "";
            String b = "";

            //查找字符串中是否有符合的子字符串
            Integer isVip = MapUtils.getInteger(param, "isVip");
            while(matcherPhone.find()){
                if(isVip != null && isVip == 1){

                    String phoneGroup = matcherPhone.group();
                    String s = phoneGroup.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                    a=s+";"+a;

                }else{
                    a=matcherPhone.group()+";"+a;
                }

            }

            //查找字符串中是否有符合的子字符串
            while(matcherFixed.find()){
                if(isVip != null && isVip == 1){
                    String fixedGroup = matcherFixed.group();
                    String fixed = fixedGroup.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                    b=fixed+";"+b;
                }else{
                    b=matcherFixed.group()+";"+b;
                }

            }
            map.put("phone",a+b);
        }
        param.put("userId", VisitInfoHolder.getUserId());


        for (Map<String, Object> stringObjectMap : listMap) {
            param.put("comId",stringObjectMap.get("comId"));
            List<String> list1 = colleCompanyNewMapper.queryYesOrNo(param);
            if (list1 != null){
                stringObjectMap.put("collected","YES") ;
            }else{
                stringObjectMap.put("collected","NO");
            }

        }



        PageInfo pageInfo = new PageInfo(listMap);
        return pageInfo;
    }


}
