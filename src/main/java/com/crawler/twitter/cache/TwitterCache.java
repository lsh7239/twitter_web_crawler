package com.crawler.twitter.cache;

import com.crawler.twitter.entity.Tweet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TwitterCache {

    private ConcurrentHashMap<String,Object> cache;

    public TwitterCache(){
        this.cache = new ConcurrentHashMap<>();
    }

    public void put(String key, Set<Tweet> value) {
        this.cache.put(key,value);
    }

    public Set<Tweet> getTweet(String key){
        return (Set<Tweet>) get(key);
    }

    public Object get(String key){
        return this.cache.get(key);
    }

    public Set<String> getKeys(){
        return this.cache.keySet();
    }
}
