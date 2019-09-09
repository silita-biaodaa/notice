package com.silita.notice.model.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告订阅
 * Created by zhushuai on 2019/9/4.
 */
@Setter
@Getter
@Document(indexName = "notice", type = "subscribe")
public class NoticeElasticsearch implements Serializable {

    @Id
    private String snatchId;
    @Field(type = FieldType.Keyword)
    private String ntId;
    @Field(type = FieldType.Keyword)
    private String title;
    @Field(type = FieldType.Keyword)
    private String content;
    @Field(type = FieldType.Keyword, fielddata = true)
    private String pubDate;
    @Field(type = FieldType.Keyword)
    private String quaName;
    @Field(type = FieldType.Keyword)
    private String quaId;
    @Field(type = FieldType.Keyword)
    private String source;
    @Field(type = FieldType.Keyword)
    private String city;
    @Field(type = FieldType.Keyword)
    private String noticeType;
    @Field(type = FieldType.Keyword)
    private String projectType;
    @Field(type = FieldType.Keyword)
    private String type;
    @Field(type = FieldType.Keyword)
    private String pbMode;
    @Field(type = FieldType.Date)
    private Date created = new Date();
}
