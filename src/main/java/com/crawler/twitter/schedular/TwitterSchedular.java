package com.crawler.twitter.schedular;

import com.crawler.twitter.cache.TwitterCache;
import com.crawler.twitter.delegator.TwitterCrawler;
import com.crawler.twitter.entity.Tweet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TwitterSchedular {

    private TwitterCrawler twitterCrawler;
    private TwitterCache cache;

    public TwitterSchedular(TwitterCrawler twitterCrawler, TwitterCache cache){
        this.twitterCrawler = twitterCrawler;
        this.cache = cache;
    }

    @Scheduled(fixedDelay = 1000 * 30) // 30초에 한번씩
    public void scheduled(){
        Set<String> keys =  this.cache.getKeys();
        for(String key : keys){
            Set<Tweet> result = twitterCrawler.search(key);
            this.cache.getTweet(key).addAll(result);
        }
    }

}
