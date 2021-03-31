package com.crawler.twitter.resource;

import com.crawler.twitter.domain.TwitterLogic;
import com.crawler.twitter.entity.Tweet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "twitter")
public class TwitterResource {

    private TwitterLogic twitterLogic;

    public TwitterResource(TwitterLogic logic){
        this.twitterLogic = logic;
    }

    @GetMapping(value="/{search}")
    public Set<Tweet> findByWords(@PathVariable("search") String word){
        return twitterLogic.searchWord(word);
    }
}
