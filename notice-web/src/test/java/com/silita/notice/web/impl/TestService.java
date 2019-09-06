package com.silita.notice.web.impl;

import com.silita.notice.model.es.NoticeElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zhushuai on 2019/9/4.
 */
public interface TestService extends ElasticsearchRepository<NoticeElasticsearch, String> {

}
