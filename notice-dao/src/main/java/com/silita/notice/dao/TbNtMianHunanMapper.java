package com.silita.notice.dao;

import com.silita.notice.model.TbNtMianHunan;
import com.silita.notice.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
@Repository
public interface TbNtMianHunanMapper extends MyMapper<TbNtMianHunan> {


    /**
     *
     * 查询中标公告
     * bids:中标
     * @param param
     * @return
     */
    //List<Map<String,Object>> queryBids(TbNtMianHunan nociteMian,List<String> list,String proviceCode);
    List<Map<String,Object>> queryBids(Map<String,Object> param);




    /**
     *通过公司名称模糊查询公司中标记录
     * bids:中标
     * cand:全国
     * @param param
     * @return
     */

    List<Map<String,Object>> queryCompanyName(Map<String,Object> param);

    /**
     * 获取点击量
     * @param param
     * @return
     */
    Integer queryCompanyCount(Map<String,Object> param);

    /**
     * 查询招标
     * @param param
     * @return
     */
   List<Map<String,Object>> queryTenders(Map<String,Object> param);

    /**
     * 获取中标公告详情
     * @param param
     * @param
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
     * 获取点击量
     * @param param
     * @return
     */
    Integer queryClickCount(Map<String,Object> param);

    //创建点击量
    void createClickCount(Map<String,Object> param);

    /**
     *获取用户是否关注
     * @return
     */
    Map<String,Object> queryAttention(Map<String,Object> param);

    /**
     *
     * 编辑点击量
     * @param param
     */

    void addClickCount(Map<String,Object> param);


    /**
     * 通过接收的资质 找到 资质等级表的id
     * @param param
     * @return
     */
    List<String> queryQuaId(Map<String,Object> param);






}