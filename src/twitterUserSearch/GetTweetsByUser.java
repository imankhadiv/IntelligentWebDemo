package twitterUserSearch;

import googlemap.FourSquare;
import googlemap.URL_Info;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;
import com.sun.xml.internal.rngom.parse.host.Base;

import Models.TweetWithURL;
import RdfModel.BaseModel;
import RdfModel.FourSquareAccount;
import RdfModel.Person;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;
import RdfModel.Venue;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweetsByUser {
	static String CLIENT_ID = "KBG4IHZCCEPFXDBBRGS5NZAE45VHUBVXY1K22W2F1C3CCH3Y";
	static String CLIENT_SECRET = "EJSGG20AKXFOUAJO1A0RTLMPLX1SZUC3NBJZKWECROT221HX";
	static String URI = "http://www.yourapp.com";
	static String tokenString = "LJIXSK34YNUO54OZKLB2XNMLQOOK32XEDEXVFMPDN4QRAJIX";

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
			String username, int daysbefore, String filePath) {
		List<TweetWithURL> tweetsList = new ArrayList<TweetWithURL>();
		try {
			int pastdays = daysbefore;
			// Query query = new Query(username);
			Query query = new Query("from:" + username
					+ " filter:links 4sq.com");
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
			BaseModel baseModel = new BaseModel();
			// System.out.println(tweets.size());
			for (Status tweet : tweets) { // /gets the user
				System.out.println(tweet.getText());
				boolean hasTweet = baseModel.hasTweetRecord(
						String.valueOf(tweet.getId()), filePath);
				if (!hasTweet) {
					twitter4j.User user = tweet.getUser();
					URLEntity[] urls = tweet.getURLEntities();
					List<String> URLlist = new ArrayList<String>();
					List<String> expandedURLlist = new ArrayList<String>();
					for (URLEntity url : urls) {
						System.out.println("1123@" + url.getDisplayURL()
								+ "/////" + url.getExpandedURL());
						String expandURL = expandUrl(url.getExpandedURL());
						if (((expandURL.startsWith("https://foursquare.com/"))
								&& (expandURL.contains("checkin")) && (expandURL
									.contains("s="))) || expandURL == null) {
							URLlist.add(url.getURL());
							expandedURLlist.add(url.getExpandedURL());

							FourSquare fs = new FourSquare();
							URL_Info currentInfo = fs
									.getLocationInformation(url.getURL());

							// save tweet to rdf
							Tweet tweetRDF = new Tweet();
							Model modelMain = tweetRDF
									.getModelFromFile(filePath);
							System.out.println("string value"+String.valueOf(tweet.getCreatedAt()));
							System.out.println("to string"+tweet.getCreatedAt().toString());
							System.out.println("to string2"+tweet.getCreatedAt().toGMTString());
							if (tweet.getRetweetedStatus() != null) {
								tweetRDF.saveTweet(
										String.valueOf(tweet.getId()), tweet
												.getText(),
										url.getExpandedURL(), String.valueOf(tweet
												.getCreatedAt()),
										String.valueOf(tweet
												.getRetweetedStatus().getId()),
										String.valueOf(tweet.getUser().getId()));
							} else {
								tweetRDF.saveTweet(
										String.valueOf(tweet.getId()),
										tweet.getText(), url.getExpandedURL(),
										String.valueOf(tweet
												.getCreatedAt()), null,
										String.valueOf(tweet.getUser().getId()));
							}
							modelMain.add(tweetRDF.getModel());
							// save twitter user to rdf
							TwitterAccount twitterAccount = new TwitterAccount();
							twitterAccount.saveTwitterAccount(user.getName(),
									String.valueOf(user.getId()),
									user.getScreenName(),
									user.getDescription(),
									user.getProfileImageURL());
							modelMain.add(twitterAccount.getModel());
							// save person to rdf
							Person person = new Person();
							person.savePerson(user.getName(),
									user.getLocation(), user.getId(),
									currentInfo.getUser().getId());
							modelMain.add(person.getModel());
							// save foursquare account
							FourSquareAccount fourSquareAccount = new FourSquareAccount();
							fourSquareAccount.saveFourSquareAccount(currentInfo
									.getUser().getId(), currentInfo.getUser()
									.getPhotoURL(), currentInfo.getUser()
									.getFirstName()
									+ currentInfo.getUser().getLastName());
							modelMain.add(fourSquareAccount.getModel());
							// save venue
							Venue venue = new Venue();
							venue.saveVenue(currentInfo.getVenue()
									.getVenue_name(), currentInfo.getVenue()
									.getAddress(), currentInfo.getVenue()
									.getPostcode(), currentInfo.getVenue()
									.getCountry(), currentInfo.getVenue()
									.getCity(), String.valueOf(currentInfo
									.getVenue().getLatitude()), String
									.valueOf(currentInfo.getVenue()
											.getLongitude()), currentInfo
									.getVenue().getPhotoURL(), String
									.valueOf(tweet.getId()));
							modelMain.add(venue.getModel());
							modelMain.write(System.out);
							System.out.println(filePath);
							tweetRDF.saveModel(filePath, modelMain);

							TweetWithURL tweetWithURL = new TweetWithURL(
									tweet.getText(), URLlist, expandedURLlist,
									tweet.getCreatedAt().toString(),String.valueOf(tweet.getId()));
							tweetsList.add(tweetWithURL);
						}
					}
				} else {
					// TODO read from rdf
					List<TweetWithURL> list = baseModel.getTweetWithURLRecordsByScreenName(username, filePath);
					System.out.println("size is : "+list.size());
					System.out.println("url is : "+ list.get(0).getDisplayURL());
					for (TweetWithURL tweetWithURL2 : list) {
						String shortString = tweetWithURL2.getDisplayURL().get(0);
						System.out.println(shortString);
						String expandURL = expandUrl(shortString);
						System.out.println("expandURL is " + expandURL);
						if (((expandURL.startsWith("https://foursquare.com/"))
								&& (expandURL.contains("checkin")) && (expandURL
									.contains("s="))) && expandURL != null) {
							tweetsList.add(tweetWithURL2);
						}
						else {
							System.out.println(expandURL);
						}
						
					}
					System.out.println("successfully read from rdf");
					
				}
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

		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/RDF.rdf";
		GetTweetsByUser tt = new GetTweetsByUser();
		List<TweetWithURL> tweetsList = new ArrayList<TweetWithURL>();

		Twitter twitterConnection = null;
		try {
			twitterConnection = tt.init();
			tweetsList = tt.getSimpleTimeLine(twitterConnection, "njy0612", 5,
					fileName);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();

		}

	}
}
