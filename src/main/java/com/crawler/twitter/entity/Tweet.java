package com.crawler.twitter.entity;

import lombok.Data;

@Data
public class Tweet {

    private String id;
    private String authorId;
    private String text;
    private String createTime;

}
