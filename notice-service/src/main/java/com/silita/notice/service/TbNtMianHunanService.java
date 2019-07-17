package com.silita.notice.service;

import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TbNtMianHunanService{


    /**
     * 查询中标公告
     * @param param
     * @return
     */
    //List<Map<String,Object>> queryBids(TbNtMianHunan nociteMian,List<String> list,String proviceCode);
    PageInfo queryBids(Map<String,Object> param);




    /**
     * 通过公司名称模糊查询公司中标记录
     * 全国
     */

    PageInfo queryCompanyName(Map<String,Object> param);

    /**
     * 获取企业中标数量
     * @param param
     * @return
     */
    Integer queryCompanyCount(Map<String,Object> param);


    /**
     * 查询招标
     * @param param
     * @return
     */
    PageInfo queryTenders(Map<String,Object> param);


    /**
     * 获取中标公告详情
     * @param param
     * @return
     */
    Map<String,Object> queryBidsNociteDetails(Map<String,Object> param);


    /**
     * 获取招标公告详情
     * @param param
     * @return
     */
    Map<String,Object> queryTendersNociteDetails(Map<String,Object> param);


    /**
     * 获取公告原文
     * @param param
     * @return
     * @throws IOException
     */
    //String queryBidsDetailsCentendString(Map<String,Object> param) throws IOException;

    /**
     * 获取评标办法   招标
     * @param pbModes
     * @return
     */
    List<String> queryPbModes(String pbModes);

    /**
     * 获取地区
     * @param regional
     * @return
     */
    Map<String,Object> queryRegional(String regional);



    /**
     * 获取点击量
     * @param param
     * @return
     */
    Integer count(Map<String,Object> param);

    /**
     * 匹配用户是否关注
     * @param param
     * @return
     */
    Map<String,Object> queryAttention(Map<String,Object> param);

    /**
     * 是否关注
     * @param param
     * @return
     */
    Boolean attention(Map<String,Object> param);

    /**
     * 获取资质id
     * @param param
     * @return
     */
    List<String> queryQuaId(Map<String,Object> param);

    //增加点击量
    void addClickCount(Map<String,Object> param);


    /**
     * 查省级编号和市级编号和爬取id
     */

    Map<String,Object> queryProviceCity(Map<String,Object> param);

    /**
     * 通过编号查询省级名称和市名称
     */
    Map<String,Object> queryProviceName(Map<String,Object> param);

    /**
     * 通过编号查询市级名称
     * @param param
     * @return
     */
    Map<String,Object> queryCityName(Map<String,Object> param);

    /**
     * 根据企业资质查询附和该资质的公告
     * @param param
     * @return
     */
    List<Map<String,Object>> queryComQuaNotice(Map<String,Object> param);




}