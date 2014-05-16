package twitterUserSearch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;
import Models.TweetWithURL;
import Models.User;

public class GetTweetID {
	public long getUserID(Twitter twitter,
			String username) {
		long id = 0;
			try {
				ResponseList<twitter4j.User> users=  twitter.searchUsers(username, 0);
				id = users.get(0).getId();
//				System.out.println(users.get(0).getName()+"  "+ id);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return id;
	}

	public Twitter init() throws Exception {
		String consumerkey = "5aUFVk9PfsdKQusWkwiOOQ";
		String consumersecret = "17Ze1q8mYSRFPgFGV6sBJrybYUrjpMYR6JmP29xvNKE";
		String accesstoken = "2365765400-PzPlz6uUcHZsDdDwbozJpvfl4CxkC4mKSzyfuCQ";
		String accesstokensecret = "DYTINSWrKwjoelp3nmBRUlzuD0EDloauLX1HGyXOtYDvT";
		Twitter twitterConnection = initTwitter(consumerkey, consumersecret,
				accesstoken, accesstokensecret);
		return twitterConnection;
	}

	private Twitter initTwitter(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(true);
		return (new TwitterFactory(cb.build()).getInstance());
	}

	public static void main(String[] args) {
		GetTweetID tt = new GetTweetID();
		
		Twitter twitterConnection = null;
		try {
			twitterConnection = tt.init();
//			System.out.println(tt.getUserID(twitterConnection, "njy0612"));
			

		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();

		}

	}
}
