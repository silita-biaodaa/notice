package com.silita.notice.elasticsearch;

import com.silita.notice.model.es.NoticeElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * Created by zhushuai on 2019/9/5.
 */
@Component
public interface ElasticsearchFactory extends ElasticsearchRepository<NoticeElasticsearch, String> {

}
