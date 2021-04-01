package com.crawler.twitter.store;

import com.crawler.twitter.entity.Tweet;
import com.crawler.twitter.store.repository.TwitterRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TwitterStoreTest {

    private TwitterStore twitterStore;

    @Autowired
    private TwitterRepository twitterRepository;

    @BeforeEach
    public void setUp(){
        this.twitterStore = new TwitterStore(twitterRepository);
    }

    @Test
    public void findById(){
        Tweet tweet = twitterStore.findById("1377621525906472963");
        Assertions.assertEquals("1287200098426839041", tweet.getAuthorId());
    }

    @Test
    public void create(){
        Tweet tweet = new Gson().fromJson("  {\n" +
                "    \"id\": \"1377626533448331264\",\n" +
                "    \"authorId\": \"1266815137480011777\",\n" +
                "    \"text\": \"그 정도야, 피카?/ㅂ\\\\ㅋㅋ #동물의숲 #AnimalCrossing #ACNH #NintendoSwitch https://t.co/J7IgUaW6Hm\",\n" +
                "    \"createTime\": \"2021-04-01T14:18:55.000Z\"\n" +
                "  }", new TypeToken<Tweet>(){}.getType());
        twitterStore.create(tweet);

        Tweet foundTweet = twitterStore.findById(tweet.getId());

        Assertions.assertEquals(tweet,foundTweet);
    }

    @Test
    public void createAll(){
        List<Tweet> tweets = new Gson().fromJson("[  {\n" +
                "    \"id\": \"1377625197264654339\",\n" +
                "    \"authorId\": \"1340959003082256385\",\n" +
                "    \"text\": \"동물의숲\uD83C\uDF86 꾸미다가 너무이뻐서 포토샵으로 만들어봤어요!! 친구하실분\\n#동물의숲 #포토샵 #AnimalCrossing #art #artwork #그림 #どうぶつの森 #どうぶつの森ポケットキャンプ https://t.co/CI1pVVedba\",\n" +
                "    \"createTime\": \"2021-04-01T14:13:36.000Z\"\n, \"key\":\"동물의숲\"" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"1377625403276283917\",\n" +
                "    \"authorId\": \"1266815137480011777\",\n" +
                "    \"text\": \"밤에 가로등 중요하지, 솔미/ㅅ\\\\ #동물의숲 #AnimalCrossing #ACNH #NintendoSwitch https://t.co/8sF26M3eCF\",\n" +
                "    \"createTime\": \"2021-04-01T14:14:25.000Z\"\n, \"key\":\"동물의숲\"" +
                "  }]", new TypeToken<List<Tweet>>(){}.getType());
        twitterStore.createAll(tweets);

        Tweet foundTweet = twitterStore.findById("1377625403276283917");
        Assertions.assertEquals(true,tweets.contains(foundTweet));
    }

    @Test
    public void findByKey(){
        List<Tweet> tweets = this.twitterStore.findByKeyword("동물의숲");
        Assertions.assertEquals(2,tweets.size());
    }

    @Test
    public void existsByKey(){
        Assertions.assertEquals(2, this.twitterStore.existsByKey("동물의숲").size());
        Assertions.assertEquals(0, this.twitterStore.existsByKey("운전만해").size());
    }
}
