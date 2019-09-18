package com.silita.notice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.silita.notice.common.VisitInfoHolder;
import com.silita.notice.dao.TbNtMianHunanMapper;
import com.silita.notice.service.TbNtMianHunanService;
import com.silita.notice.utils.DateUtils;
import com.silita.notice.utils.Page;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * es查询
 * Created by zhushuai on 2019/9/6.
 */
@Service
public class ElasticsearchService {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    TbNtMianHunanService tbNtMianHunanService;
    @Autowired
    TbNtMianHunanMapper tbNtMianHunanMapper;

    public Map<String, Object> getSubscribe(Map<String, Object> param, String opt) {
        Map<String, Object> resultMap = new HashedMap() {{
            put("pages", 0);
            put("total", 0);
            put("pageNo", 0);
            put("pageSize", 0);
            put("data", new ArrayList<>(1));
        }};
        if (MapUtils.isEmpty(param)) {
            return resultMap;
        }
        Integer pageNo = MapUtils.getInteger(param, "pageNo");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Page page = new Page(pageNo, pageSize);
        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch("notice").setTypes("subscribe");
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        //地区
        if (null != param.get("regions")) {
            String[] pros = MapUtils.getString(param, "regions").split(";");
            BoolQueryBuilder proQuery = QueryBuilders.boolQuery();
            int proLength = pros.length;
            int cityLength = 0;
            String[] citys;
            String[] proCitys;
            for (int i = 0; i < proLength; i++) {
                proCitys = pros[i].split("\\|\\|");
                if (null != proCitys && proCitys.length > 1) {
                    citys = proCitys[1].split(",");
                    BoolQueryBuilder proCityQuery = QueryBuilders.boolQuery();
                    BoolQueryBuilder cityQuery = QueryBuilders.boolQuery();
                    cityLength = citys.length;
                    for (int j = 0; j < cityLength; j++) {
                        cityQuery.should(new QueryStringQueryBuilder("\"" + citys[j] + "\"").field("city"));
                    }
                    proCityQuery.must(cityQuery);
                    proCityQuery.must(new QueryStringQueryBuilder("\"" + proCitys[0] + "\"").field("source"));
                    proQuery.should(proCityQuery);
                } else {
                    proQuery.should(new QueryStringQueryBuilder("\"" + proCitys[0] + "\"").field("source"));
                }
            }
            query.must(proQuery);
        }
        //关键词
        if (null != param.get("keywords")) {
            BoolQueryBuilder keywordsQuery = QueryBuilders.boolQuery();
            String[] keywords = MapUtils.getString(param, "keywords").split(",");
            int keywordsLeng = keywords.length;
            for (int i = 0; i < keywordsLeng; i++) {
                keywordsQuery.should(new QueryStringQueryBuilder("\"" + keywords[i] + "\"").field("title").field("content"));
            }
            query.must(keywordsQuery);
            // 设置高亮显示
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.field("content");
            highlightBuilder.preTags("<font color='red'>");
            highlightBuilder.postTags("</font>");
            highlightBuilder.fragmentSize(5);
            searchRequestBuilder.highlighter(highlightBuilder);
        }
        //资质
        if (null != param.get("qualCode")) {
            List<String> quals = tbNtMianHunanService.setNoticeQual(MapUtils.getString(param, "qualCode"));
            if (null != quals && quals.size() > 0) {
                BoolQueryBuilder qualQuery = QueryBuilders.boolQuery();
                int quaLength = quals.size();
                for (int i = 0; i < quaLength; i++) {
                    qualQuery.should(new QueryStringQueryBuilder("\"" + quals.get(i) + "\"").field("quaId"));
                }
                query.must(qualQuery);
            }
        }
        BoolQueryBuilder date = QueryBuilders.boolQuery();
        if ("show".equals(opt)) {
            //查询公示日期
            String now = DateUtils.dateToStr(new Date(), "yyyy-MM-dd");
            date.must(QueryBuilders.rangeQuery("pubDate").from(DateUtils.beforeDate(now, 7)).to(now));
            query.must(date);
        } else {
            //查询入es时间
            long start = MapUtils.getLong(param, "start");
            long end = MapUtils.getLong(param, "end");
            date.must(QueryBuilders.rangeQuery("created").from(start).to(end));
            query.must(date);
        }
        searchRequestBuilder.setQuery(query);
        String[] filed = new String[]{"ntId", "snatchId", "title", "pubDate", "quaName", "source", "noticeType", "projectType", "type", "pbMode", "regions"};
        searchRequestBuilder.setFetchSource(filed, null);
        searchRequestBuilder.setFrom(page.getPageNo()).setSize(page.getPageSize());
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        // 执行搜索,返回搜索响应信息
        SearchResponse response = searchRequestBuilder.addSort("pubDate", SortOrder.DESC).execute().actionGet();
        // 获取搜索的文档结果
        SearchHits searchHits = response.getHits();
        SearchHit[] res = searchHits.getHits();
        page.setTotal(Integer.parseInt(String.valueOf(searchHits.totalHits)));
        resultMap.put("pages", page.getPages());
        resultMap.put("total", page.getTotal());
        resultMap.put("pageNo", pageNo);
        resultMap.put("pageSize", pageSize);
        resultMap.put("data", formatResult(res, param, opt));
        return resultMap;
    }

    /**
     * 格式化es查询结果
     *
     * @param res
     * @return
     */
    private List formatResult(SearchHit[] res, Map<String, Object> param, String opt) {
        String keywords = MapUtils.getString(param, "keywords");
        int length = res.length;
        List<Map<String, Object>> list = new ArrayList<>(length);
        Map<String, Object> valMap;
        int count;
        StringBuffer content = new StringBuffer("");
        StringBuffer title = new StringBuffer("");
        List<Map<String, Object>> keywordsMap = new ArrayList<>(1);
        Map<String, Object> keyMap;
        String[] words;
        for (int i = 0; i < length; i++) {
            if (StringUtils.isNotEmpty(keywords)) {
                words = keywords.split(",");
                int wordsLength = words.length;
                keywordsMap = new ArrayList<>(wordsLength);
                // 获取对应的高亮域
                Map<String, HighlightField> result = res[i].getHighlightFields();
                // 从设定的高亮域中取得指定域
                HighlightField titleField = result.get("title");
                if (titleField != null) {
                    // 取得定义的高亮标签
                    Text[] titleTexts = titleField.fragments();
                    // 为title串值增加自定义的高亮标签
                    for (Text text : titleTexts) {
                        title.append(text);
                    }
                }
                if (StringUtils.isNotEmpty(title.toString())) {
                    title = new StringBuffer(Jsoup.parse(title.toString()).text());
                    for (int key = 0; key < wordsLength; key++) {
                        keyMap = new HashedMap(1);
                        keyMap.put("word", words[key]);
                        keyMap.put("isExist", 0);
                        if (title.toString().indexOf(words[key]) > -1) {
                            keyMap.put("isExist", 1);
                        }
                        keywordsMap.add(keyMap);
                    }
                }
                HighlightField contentField = result.get("content");
                if (contentField != null) {
                    // 取得定义的高亮标签
                    Text[] contentTexts = contentField.fragments();
                    // 为content串值增加自定义的高亮标签
                    content.append(contentTexts[0]);
                }
            }
            valMap = JSONObject.parseObject(res[i].getSourceAsString());
            valMap.put("subType", "zhaobiao");
            valMap.put("pkid", valMap.get("ntId"));
            if ("show".equals(opt)) {
                valMap.put("isRead", 0);
                valMap.put("userId", VisitInfoHolder.getUserId());
                count = tbNtMianHunanMapper.queryNoticeReadStatus(valMap);
                if (count > 0) {
                    valMap.put("isRead", 1);
                }
                valMap.remove("userId");
            }
            if (StringUtils.isNotEmpty(content.toString())) {
                valMap.put("content", "..." + handleContent(content.toString(), "<font color='red'>") + "...");
            }
            valMap.put("keywords", keywordsMap);
            list.add(valMap);
        }
        return list;
    }

    /**
     * 截取内容
     *
     * @param content
     * @param tags
     * @return
     */
    private String handleContent(String content, String tags) {
        int index = content.indexOf(tags);
        return content.substring(index, content.length());
    }

}
