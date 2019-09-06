package com.silita.notice.web;

import com.silita.notice.common.RegionCommon;
import com.silita.notice.dao.TbNtMianHunanMapper;
import com.silita.notice.model.es.NoticeElasticsearch;
import com.silita.notice.web.impl.TestService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhushuai on 2019/9/4.
 */
public class ESTest extends BaseCastTest {

    @Autowired
    ElasticsearchTemplate template;
    @Autowired
    TbNtMianHunanMapper tbNtMianHunanMapper;
    @Autowired
    TestService testService;

    @Test
    public void createdIndex() {
        template.createIndex(NoticeElasticsearch.class);
    }

    @Test
    public void delIndex() {
        template.deleteIndex(NoticeElasticsearch.class);
    }

    @Test
    public void saveData() {
        Map<String, Object> param = new HashMap<>();
        for (Object val : RegionCommon.regionSourcePinYin.values()) {
            param.put("source", val);
            param.put("pdModeType", val + "_pbmode");
            List<NoticeElasticsearch> list = tbNtMianHunanMapper.queryNotice(param);
            testService.saveAll(list);
        }
    }

    @Test
    public void query() {
        SearchRequestBuilder searchRequestBuilder = template.getClient().prepareSearch("notice").setTypes("subscribe");
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        //关键字
        BoolQueryBuilder keywords = QueryBuilders.boolQuery();
        keywords.should(new QueryStringQueryBuilder("\"蓄水\"").field("title").field("content"));
        keywords.should(new QueryStringQueryBuilder("\"神农架\"").field("title").field("content"));
        keywords.should(new QueryStringQueryBuilder("\"会以\"").field("title").field("content"));
        query.must(keywords);
        //地区
        BoolQueryBuilder regions = QueryBuilders.boolQuery();
        BoolQueryBuilder proCity = QueryBuilders.boolQuery();
        proCity.must(new QueryStringQueryBuilder("\"hunan\"").field("source"));
        proCity.must(new QueryStringQueryBuilder("\"changsha\"").field("city"));
        regions.must(proCity);
        query.must(regions);
        //资质
        BoolQueryBuilder quals = QueryBuilders.boolQuery();
        quals.should(new QueryStringQueryBuilder("\"5b3951aa34264930b05efd1542c0efe1\"").field("quaId"));
        query.must(quals);
        //只查近七天
        BoolQueryBuilder date = QueryBuilders.boolQuery();
        date.must(QueryBuilders.rangeQuery("pubDate").from("2019-09-02").to("2019-09-06"));
        query.must(date);
        searchRequestBuilder.setQuery(query);
        searchRequestBuilder.setFrom(0).setSize(20);
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        // 设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.field("content");
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchRequestBuilder.highlighter(highlightBuilder);
        // 执行搜索,返回搜索响应信息
        SearchResponse response = searchRequestBuilder.addSort("pubDate", SortOrder.DESC).execute().actionGet();
        // 获取搜索的文档结果
        SearchHits searchHits = response.getHits();
        SearchHit[] res = searchHits.getHits();
        System.out.println("当页数:" + searchHits.getHits().length);
        System.out.println("总条数:" + searchHits.totalHits);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i].getSourceAsString());
        }
    }


}
