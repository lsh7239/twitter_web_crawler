package com.crawler.twitter.store;

import com.crawler.twitter.entity.Tweet;
import com.crawler.twitter.store.repository.TwitterRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableElasticsearchRepositories
public class TwitterStore {

    private TwitterRepository twitterRepository;

    public TwitterStore(TwitterRepository twitterRepository){
        this.twitterRepository = twitterRepository;
    }

    public Tweet findById(String id){
        Optional<Tweet> optional = twitterRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void create(Tweet tweet){
        this.twitterRepository.save(tweet);
    }

    public void createAll(Iterable<Tweet> tweets){
        this.twitterRepository.saveAll(tweets);
    }

    public List<Tweet> findByKeyword(String key){
        return this.twitterRepository.findByKey(key);
    }

    public List<Tweet> existsByKey(String key){
        return this.twitterRepository.existsByKey(key);
    }
}
