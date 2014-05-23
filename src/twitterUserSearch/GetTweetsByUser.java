package twitterUserSearch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import Models.TweetWithURL;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweetsByUser {
	static String CLIENT_ID="KBG4IHZCCEPFXDBBRGS5NZAE45VHUBVXY1K22W2F1C3CCH3Y";
	static String CLIENT_SECRET="EJSGG20AKXFOUAJO1A0RTLMPLX1SZUC3NBJZKWECROT221HX";
	static String URI="http://www.yourapp.com";
	static String tokenString="LJIXSK34YNUO54OZKLB2XNMLQOOK32XEDEXVFMPDN4QRAJIX";
	
	private String getFullURL(String shortURLs) throws IOException {
		URL shortUrl = new URL(shortURLs);
		final HttpURLConnection httpURLConnection = (HttpURLConnection) shortUrl
				.openConnection();
		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.connect();
		final String header = httpURLConnection.getHeaderField("Location");
		return header;
	}

	private String expandUrl(String shortURLs) {
		String url = shortURLs;
		// String initialUrl = url;
		while (url != null) {
			try {
				url = getFullURL(shortURLs);
				if (url != null)
					shortURLs = url;
				else {
					url = shortURLs;
					break;
				}
			} catch (IOException e) {
				// this is not a tiny URL as it is not redirected!
				break;
			}
		}
		return url;
	}
	
	public List<TweetWithURL> getSimpleTimeLine(Twitter twitter,
			String username, int daysbefore) {
		List<TweetWithURL> tweetsList = new ArrayList<TweetWithURL>();
		try {
			int pastdays = daysbefore;
			Query query = new Query("from:" + username + " filter:links 4sq.com");

			Calendar since = Calendar.getInstance();
			since.add(Calendar.DATE, (-1) * pastdays);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String sinceString = format1.format(since.getTime());
			System.out.println(sinceString);
			query.setSince(sinceString);
			query.setCount(100);
			QueryResult result = twitter.search(query);
			// it cycles on the tweets
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) { // /gets the user
				URLEntity[] urls = tweet.getURLEntities();
				List<String> URLlist = new ArrayList<String>();
				List<String> expandedURLlist = new ArrayList<String>();
				for (URLEntity url : urls) {
					String shortURL = expandUrl(url.getExpandedURL());
					if (((shortURL.startsWith("https://foursquare.com/"))
							&& (shortURL.contains("checkin")) && (shortURL.contains("s=")))||shortURL == null) {
						URLlist.add(url.getURL());
						expandedURLlist.add(url.getExpandedURL());
						TweetWithURL tweetWithURL = new TweetWithURL(tweet.getText(), URLlist, expandedURLlist, tweet.getCreatedAt());
						tweetsList.add(tweetWithURL);
					}
				}
//				System.out.println(tweet.getText());
				// create model and add to list
				
			}
		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" + te.getMessage());
			System.exit(-1);
		}
		return tweetsList;
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
		GetTweetsByUser tt = new GetTweetsByUser();
		List<TweetWithURL> tweetsList = new ArrayList<TweetWithURL>();
		
		Twitter twitterConnection = null;
		try {
			twitterConnection = tt.init();
			tweetsList = tt.getSimpleTimeLine(twitterConnection, "njy0612",
					5);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();

		}

	}
}
