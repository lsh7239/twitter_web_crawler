package com.crawler.twitter.store.repository;

import com.crawler.twitter.entity.Tweet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TwitterRepository extends ElasticsearchRepository<Tweet, String> {

    public List<Tweet> findByKey(String key);

    public List<Tweet> existsByKey(String key);
}
