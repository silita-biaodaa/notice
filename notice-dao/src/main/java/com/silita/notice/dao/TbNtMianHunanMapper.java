package com.silita.notice.dao;

import com.silita.notice.model.TbNtMianHunan;
import com.silita.notice.model.es.NoticeElasticsearch;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

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
    List<Map<String,String>> queryBids(Map<String,Object> param);

    /**
     *通过公司名称模糊查询公司中标记录
     * bids:中标
     * cand:全国
     * @param param
     * @return
     */
    List<Map<String,String>> queryCompanyName(Map<String,Object> param);

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
     * 通过公告id查询公告
     * @param regexId
     * @return
     */
    List<Map<String,Object>> queryNtId(String regexId);

    /**
     * 获取公告类型和项目类型
     * @param param
     * @return
     */
    Map<String,Object> queryProjectTypeNoticeType(Map<String,Object> param);

    List<String> queryPkid(Map<String,Object> param);

    /**
     * 查询某一天的爬取id
     * @param param
     * @return
     */
    List<String> querySnatchId(Map<String,Object> param);

 /**
  * 查询公告
  *
  * @return
  */
 List<NoticeElasticsearch> queryNotice(Map<String, Object> param);

 /**
  * 根据爬取id查询主键id
  *
  * @param param
  * @return
  */
 String queryPkidBySnatchId(Map<String, Object> param);

 /**
  * 查询公告已读状态
  *
  * @param param
  * @return
  */
 int queryNoticeReadStatus(Map<String, Object> param);
}