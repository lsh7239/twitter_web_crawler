package com.crawler.twitter.domain;

import com.crawler.twitter.cache.TwitterCache;
import com.crawler.twitter.delegator.TwitterCrawler;
import com.crawler.twitter.entity.Tweet;
import com.crawler.twitter.store.TwitterStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TwitterLogic {

    private TwitterCrawler crawler;
    private TwitterCache cache;
    private TwitterStore twitterStore;

    public TwitterLogic(TwitterCrawler crawler, TwitterCache cache, TwitterStore store){
        this.crawler = crawler;
        this.cache = cache;
        this.twitterStore = store;
    }

    public List<Tweet> searchWord(String word){

        if(cache.getTweet(word) == null){
            //cache.put(word, crawler.search(word));
            Set<Tweet> tweets = crawler.search(word);
            twitterStore.createAll(tweets);
        }
        return twitterStore.findByKeyword(word);
    }
}
