package com.silita.notice.service;

import com.github.pagehelper.PageInfo;
import com.silita.notice.model.TbNtMianHunan;
import org.apache.hbase.thirdparty.io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import org.springframework.web.bind.annotation.RequestBody;

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
    List<Map<String,Object>> queryBids(Map<String,Object> param);




    /**
     * 通过公司名称模糊查询公司中标记录
     * 全国
     */

    List<Map<String,Object>> queryCompanyName(Map<String,Object> param);

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




    public String queryBidsDetailsCentendString(Map<String,Object> param) throws IOException;

    /**
     * 获取评标办法   招标
     * @param pbModes
     * @return
     */
    public List<String> queryPbModes(String pbModes);

    /**
     * 获取地区
     * @param regional
     * @return
     */
    public Map<String,Object> queryRegional(String regional);


    /**
     * 获取点击量
     * @param param
     * @return
     */
    Map<String,Object> queryClickCount(Map<String,Object> param);

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


    void addClickCount(Map<String,Object> param);




}