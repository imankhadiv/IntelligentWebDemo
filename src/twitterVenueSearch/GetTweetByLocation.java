package twitterVenueSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.google.gson.Gson;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweetByLocation {
	public List<Models.User> getSimpleTimeLine(Twitter twitter,double latitude,double longitude,int range,String sinceString){	
		List<Models.User> userlist = new ArrayList<Models.User>();
	 try {	
	 // it creates a query and sets the geocode	
		 	 //requirement	
		 	 Query query= new Query();	
		 	 query.setSince(sinceString);
		 	 query.setGeoCode( new GeoLocation(latitude, longitude), range,	
		 	 Query.KILOMETERS);	
		 	 System.out.println(query.toString());
			query.setCount(100);
			// it fires the query
			QueryResult result = twitter.search(query);

			// it cycles on the tweets
			List<Status> tweets = result.getTweets();
			HashSet<String> idSet = new HashSet<String>();
			for (Status tweet : tweets) { // /gets the user
				User user = tweet.getUser();
				Models.User currentUser = new Models.User(user.getName(), "@"+user.getScreenName(),
						user.getLocation(), user.getDescription(), user.getProfileImageURL()); 
				
				if(!idSet.contains(currentUser.getId()))
				{
					idSet.add(currentUser.getId());
					userlist.add(currentUser);
				}
			}
		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" + te.getMessage());
			System.exit(-1);
		}
	 
	 	Gson gson =  new Gson();
	 	String jsonString = gson.toJson(userlist);
	 	System.out.println("json"+jsonString);
		return userlist;
	}

	public Twitter init() throws Exception{	
		 String consumerkey = "5aUFVk9PfsdKQusWkwiOOQ";	
		 String consumersecret = "17Ze1q8mYSRFPgFGV6sBJrybYUrjpMYR6JmP29xvNKE";	
		 String accesstoken = "2365765400-PzPlz6uUcHZsDdDwbozJpvfl4CxkC4mKSzyfuCQ";	
		 String accesstokensecret = "DYTINSWrKwjoelp3nmBRUlzuD0EDloauLX1HGyXOtYDvT";	
			 Twitter twitterConnection = initTwitter(consumerkey, 	
														 consumersecret, accesstoken, accesstokensecret);	
			 return twitterConnection;	
			 }	private Twitter initTwitter(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(true);
		return (new TwitterFactory(cb.build()).getInstance());
	}

//	public static void main(String[] args) {
//		GetTweetByLocation tt = new GetTweetByLocation();
//		Twitter twitterConnection = null;
//		try {
//			twitterConnection = tt.init();
//			
//			List<Models.User> myuserList = new ArrayList<Models.User>();
//			myuserList = tt.getSimpleTimeLine(twitterConnection,53.38112899999999,-1.47008500000004,2,"2014-03-12");
//			Gson gson = new Gson();
//			String json = gson.toJson(myuserList);
//
//		} catch (Exception e) {
//			System.out.println("Cannot initialise Twitter");
//			e.printStackTrace();
//
//		}
//
//	}
}
