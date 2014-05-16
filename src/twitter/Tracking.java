package twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import beans.Person;

public class Tracking {

	private String query;
	private GeoLocation geoLocation;
	private int radious = 100;
	private List<Person> people = new ArrayList<Person>();
	private List<Person> retwittPeople = new ArrayList<Person>();
	private boolean defaultLocation;

	public Tracking() {

	}

	public Tracking(String query, GeoLocation geoLocation) {
		this.query = query;
		this.geoLocation = geoLocation;
	}

	public Tracking(String query) {
		this.query = query;
	}

	public List<Person> getTimeLine(Twitter twitter) {
		Person person = null;
		try {
			Query q = new Query(query);
			if ((geoLocation == null) && (defaultLocation)) {
				q.setGeoCode(new GeoLocation(53.3836, -1.4669), radious,
						Query.KILOMETERS);
			} else if (geoLocation == null) {

			} else if (geoLocation != null)
				q.setGeoCode(geoLocation, radious, Query.KILOMETERS);

			QueryResult result = twitter.search(q);

			// it cycles on the tweets
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) { // /gets the user
				User user = tweet.getUser();
				person = new Person();
				person.setName(user.getName());
				person.setScreenName(user.getScreenName());
				person.setProfilePicture(user.getOriginalProfileImageURL());

				person.setTwittCount(tweet.getRetweetCount());

				Status status = (user.isGeoEnabled()) ? user.getStatus() : null;
				if (status == null) {
					person.setTwittText(tweet.getText());

				} else {
					person.setTwittText(tweet.getText());
					person.setLocation(user.getLocation());
				}
				System.out.println(person);
				if ((!tweet.isRetweet())
						&& (!tweet.getText().matches(".*query.*")))
					people.add(person);
				else
					retwittPeople.add(person);
			}
		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" + te.getMessage());
			System.exit(-1);
		}
		return people;
	}

	private Twitter init() throws Exception {
		String consumerkey = "u7F2NWTzyd3t6YCCy1uEw";
		String consumersecret = "N8xk0IIBoBshR47T9EP3teXtgkblMBsiuDVAGJvVEbQ";
		String accesstoken = "1013000588-jeXwNa502Fgs1McucwhFP8aBhLnfTeVNFWqh1F1";
		String accesstokensecret = "VIY7lfFfXuTkkQkV5b5BpkULKuVAGslmTgROxWal2eUVT";
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

	public List<Person> getRetwittPeople() {
		return retwittPeople;
	}

	public List<Person> getUsers() {

		Twitter twitterConnection = null;
		try {
			twitterConnection = init();
			System.out.println(twitterConnection);
			return getTimeLine(twitterConnection);

		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter:" + e.getMessage());
			return null;

		}
	}

	public void setRadious(int radious) {
		this.radious = radious;
	}

	public void setDefaultLocation(boolean defaultLocation) {
		this.defaultLocation = defaultLocation;
	}

}
