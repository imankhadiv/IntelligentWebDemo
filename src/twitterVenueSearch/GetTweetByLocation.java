package twitterVenueSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import RdfModel.BaseModel;
import RdfModel.Person;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;

import com.google.gson.Gson;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweetByLocation {
	/**
	 * 
	 * @param twitter
	 * @param latitude
	 * @param longitude
	 * @param range
	 * @param sinceString
	 * @param filePath
	 * @return
	 */
	public List<Models.User> getSimpleTimeLine(Twitter twitter,
			double latitude, double longitude, int range, String sinceString,
			String filePath) {
		List<Models.User> userlist = new ArrayList<Models.User>();
		try {
			// it creates a query and sets the geocode
			// requirement
			Query query = new Query();
			query.setSince(sinceString);
			query.setGeoCode(new GeoLocation(latitude, longitude), range,
					Query.KILOMETERS);
			System.out.println(query.toString());
			query.setCount(100);
			// it fires the query
			QueryResult result = twitter.search(query);

			// it cycles on the tweets
			List<Status> tweets = result.getTweets();
			HashSet<String> idSet = new HashSet<String>();
			BaseModel baseModel = new BaseModel();
			for (Status tweet : tweets) { // /gets the user
				boolean hasTweet = baseModel.hasTweetRecord(
						String.valueOf(tweet.getId()), filePath);
				if (!hasTweet) {
					User user = tweet.getUser();
					Models.User currentUser = new Models.User(user.getName(),
							"@" + user.getScreenName(), user.getLocation(),
							user.getDescription(), user.getProfileImageURL(),String.valueOf(user.getId()));

					Tweet tweetRDF = new Tweet();
					Model modelMain = tweetRDF.getModelFromFile(filePath);
					if (tweet.getRetweetedStatus() != null) {
						tweetRDF.saveTweet(String.valueOf(tweet.getId()), tweet
								.getText(), tweet.getCreatedAt().toString(),
								String.valueOf(tweet.getRetweetedStatus()
										.getId()), String.valueOf(tweet
										.getUser().getId()));
					} else {
						tweetRDF.saveTweet(String.valueOf(tweet.getId()), tweet
								.getText(), tweet.getCreatedAt().toString(),
								null, String.valueOf(tweet.getUser().getId()));
					}
					modelMain.add(tweetRDF.getModel());
					// save twitter user to rdf
					TwitterAccount twitterAccount = new TwitterAccount();
					twitterAccount.saveTwitterAccount(user.getName(),
							String.valueOf(user.getId()), user.getScreenName(),
							user.getDescription(), user.getProfileImageURL());
					modelMain.add(twitterAccount.getModel());
					// save person to rdf
					Person person = new Person();
					person.savePerson(user.getName(), user.getLocation(),
							user.getId());
					modelMain.add(person.getModel());
					tweetRDF.saveModel(filePath, modelMain);

					if (!idSet.contains(currentUser.getId())) {
						idSet.add(currentUser.getId());
						userlist.add(currentUser);
					}
				} else {
					// TODO read from RDF file
					
				}
			}
		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" + te.getMessage());
			System.exit(-1);
		}

		Gson gson = new Gson();
		String jsonString = gson.toJson(userlist);
		System.out.println("json" + jsonString);
		return userlist;
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

	// public static void main(String[] args) {
	// GetTweetByLocation tt = new GetTweetByLocation();
	// Twitter twitterConnection = null;
	// try {
	// twitterConnection = tt.init();
	//
	// List<Models.User> myuserList = new ArrayList<Models.User>();
	// myuserList =
	// tt.getSimpleTimeLine(twitterConnection,53.38112899999999,-1.47008500000004,2,"2014-03-12");
	// Gson gson = new Gson();
	// String json = gson.toJson(myuserList);
	//
	// } catch (Exception e) {
	// System.out.println("Cannot initialise Twitter");
	// e.printStackTrace();
	//
	// }
	//
	// }
}
