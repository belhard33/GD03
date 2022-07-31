import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/******************************************************************************
 *        Group: GD03 - Live Data Corpus
 *         Date: 03 August 2022
 *       Course: IT-516 Data Structures & Algorithms
 *   Assignment: Final Project
 *  Compile...
 *  javac -cp "../twitter4j-core-4.0.7.jar" TwitterTest.java
 *
 *  Use...
 *  java -cp "../twitter4j-core-4.0.7.jar" TwitterTest.java
 *
 *
 ******************************************************************************/

public class TwitterTest {
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                //access key and token to connect with twitter account
                .setOAuthConsumerKey("cG53fsyXLAWOg29RvRBQpK8j5")
                .setOAuthConsumerSecret("dv0Xd1RtzZVEkC0UonZzgsHksSVttT0h5saBr76BDRxV4g7MV6")
                .setOAuthAccessToken("1943036924-1czOO3B1zajcYNIS8BzMpuBHZTsgM1SKfAtzc2H")
                .setOAuthAccessTokenSecret("sWzt0fPnvruUjWofgxZp5CgAQqj61TiR4XQ8zuoI5ZkDj");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {

            //for (int i = 1; i < 7; i++) {
            // query helps to filter games related data
            Query query = new Query("Games"); //Keyword
            query.setCount(100); //Number of Tweets
            query.setLang("en");
            //query.setSince("2021-05-14"); //Back date
            //query.setUntil("2022-07-15"); //Until date

            QueryResult result;
            result = twitter.search(query);
            //list to store data fom twitter
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                System.out.println(tweet.getText() + " Location: " + tweet.getUser().getLocation() + " Language:" + tweet.getLang() + " DATE: " + tweet.getCreatedAt());
            }

            //}
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }

    }
}
