package com.crawler.twitter.domain;

import com.crawler.twitter.cache.TwitterCache;
import com.crawler.twitter.delegator.TwitterCrawler;
import com.crawler.twitter.entity.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TwitterLogic {

    private TwitterCrawler crawler;
    private TwitterCache cache;

    public TwitterLogic(TwitterCrawler crawler, TwitterCache cache){
        this.crawler = crawler;
        this.cache = cache;
    }

    public Set<Tweet> searchWord(String word){

        if(cache.getTweet(word) == null){
            cache.put(word, crawler.search(word));
        }
        return cache.getTweet(word);
    }
}
