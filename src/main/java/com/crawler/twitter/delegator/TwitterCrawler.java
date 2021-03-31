package com.crawler.twitter.delegator;


import com.crawler.twitter.entity.Tweet;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TwitterCrawler {

    @Value("${oauth.bearerToken}")
    private String bearerToken;

    private Logger logger = LoggerFactory.getLogger(TwitterCrawler.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAFeqOAEAAAAAreZAnyRzGY1PxxcBLlBZEKZO6PI%3D7YVZlVsSulVzdVB0qUsz4t9Md6jmfSaimS9BbK4vRJMeMkHixY";
        //System.getenv("BEARER_TOKEN");
        if (null != bearerToken) {
            //Replace the search term with a term of your choice
            String searchString = "방탄소년단";
            Set<Tweet> response = new TwitterCrawler().search(searchString);
            System.out.println(new Gson().toJson(response));
        } else {
            System.out.println("There was a problem getting you bearer token. Please make sure you set the BEARER_TOKEN environment variable");
        }

    }

    /*
     * This method calls the recent search endpoint with a the search term passed to it as a query parameter
     * */
    public Set<Tweet> search(String searchString){

        logger.info(String.format("start search %s",searchString));

        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAFeqOAEAAAAAreZAnyRzGY1PxxcBLlBZEKZO6PI%3D7YVZlVsSulVzdVB0qUsz4t9Md6jmfSaimS9BbK4vRJMeMkHixY";

        try{
//            URL url = new URL("https://api.twitter.com/2/tweets/search/recent?query="+URLEncoder.encode(searchString,"UTF-8")+"&max_results=20&tweet.fields=author_id,created_at");
//            URL url = new URL("https://api.twitter.com/2/tweets/search/stream/rules"+"?tweet.fields=author_id,created_at");
            URL url = new URL("https://api.twitter.com/2/tweets/search/stream"+"?tweet.fields=author_id,created_at");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization","Bearer "+bearerToken);

            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod("GET");

//            conn.setDoOutput(true);
//            try(OutputStream os = conn.getOutputStream()){
//                os.write(("{\n" +
//                        "  \"add\": [\n" +
//                        "    {\"value\": \""+searchString+"\"}\n" +
//                        "  ]\n" +
//                        "}").getBytes());
//                os.flush();
//            }catch (Exception e){
//                throw new RuntimeException(e);
//            }

            StringBuffer response = new StringBuffer();

            try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                String output = null;
                while ((output = in.readLine()) != null) {
                    System.out.println(output);
                    response.append(output);
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }

            Map<String,Object> map = new Gson().fromJson(response.toString(), new TypeToken<Map<String,Object>>(){}.getType());
            List<Map<String,String>> data = (List<Map<String, String>>) map.get("data");
            return data.stream().map(m -> {
                Tweet tweet = new Tweet();
                tweet.setCreateTime(m.get("created_at"));
                tweet.setAuthorId(m.get("author_id"));
                tweet.setId(m.get("id"));
                tweet.setText(m.get("text"));
                return tweet;
            }).collect(Collectors.toSet());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
