package com.crawler.twitter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "tweet")
public class Tweet {

    @Id
    private String id;

    @Field(type=FieldType.Text)
    private String key;

    @Field(type = FieldType.Text)
    private String authorId;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Text)
    private String createTime;

}
