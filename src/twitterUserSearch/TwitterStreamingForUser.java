package twitterUserSearch;

import googlemap.FourSquare;
import googlemap.URL_Info;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model;

import Models.TweetWithURL;
import RdfModel.FourSquareAccount;
import RdfModel.Person;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;
import RdfModel.Venue;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitterVenueSearch.MyDB;
import twitterVenueSearch.TwitterStreaming;

public class TwitterStreamingForUser {
		 private String consumerkey = "5aUFVk9PfsdKQusWkwiOOQ";	
		 private String consumersecret = "17Ze1q8mYSRFPgFGV6sBJrybYUrjpMYR6JmP29xvNKE";	
		 private String accesstoken = "2365765400-PzPlz6uUcHZsDdDwbozJpvfl4CxkC4mKSzyfuCQ";	
		 private String accesstokensecret = "DYTINSWrKwjoelp3nmBRUlzuD0EDloauLX1HGyXOtYDvT";	
		 private List<TweetWithURL> uRLList;
		 
		 
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
		 
		 
		 public List<TweetWithURL> getuRLList() {
			return uRLList;
		}

		public void setuRLList(List<TweetWithURL> uRLList) {
			this.uRLList = uRLList;
		}


		public TwitterStreamingForUser(){
			 uRLList = new ArrayList<TweetWithURL>();
		 }
		 
		 
		 TwitterStream getTwitterStream(final String filePath) throws Exception {
			final TwitterStream twitterStream = initTwitterStream(consumerkey,
					consumersecret, accesstoken, accesstokensecret);
			StatusListener listener = new StatusListener() {
				@Override
				public void onStatus(Status status) {
					User user = status.getUser();
					URLEntity[] urls = status.getURLEntities();
					List<String> URLlist = new ArrayList<String>();
					List<String> expandedURLlist = new ArrayList<String>();
					for (URLEntity url : urls) {
						String shortURL = expandUrl(url.getExpandedURL());
						if (((shortURL.startsWith("https://foursquare.com/"))
								&& (shortURL.contains("checkin")) && (shortURL.contains("s=")))||shortURL == null) {
							URLlist.add(url.getURL());
							expandedURLlist.add(url.getExpandedURL());
							
							FourSquare fs = new FourSquare();
							URL_Info currentInfo = fs.getLocationInformation(url
									.getURL());
							
							// save tweet to rdf
							Tweet tweetRDF = new Tweet();
							Model modelMain = tweetRDF.getModelFromFile(filePath);
							if(status.getRetweetedStatus()!=null)
							{
								tweetRDF.saveTweet(String.valueOf(status.getId()), status
										.getText(), url.getDisplayURL(), status
										.getCreatedAt().toString(), String
										.valueOf(status.getRetweetedStatus().getId()),
										String.valueOf(status.getUser().getId()));
							}
							else {
								tweetRDF.saveTweet(String.valueOf(status.getId()), status
										.getText(), url.getDisplayURL(), status
										.getCreatedAt().toString(), null,
										String.valueOf(status.getUser().getId()));
							}
							modelMain.add(tweetRDF.getModel());
							// save twitter user to rdf
							TwitterAccount twitterAccount = new TwitterAccount();
							twitterAccount.saveTwitterAccount(user.getName(),
									String.valueOf(user.getId()),
									user.getScreenName(), user.getDescription(),
									user.getProfileImageURL());
							modelMain.add(twitterAccount.getModel());
							// save person to rdf
							Person person = new Person();
							person.savePerson(user.getName(), user.getLocation(),
									user.getId(), currentInfo.getUser().getId());
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
							venue.saveVenue(currentInfo.getVenue().getVenue_name(),
									currentInfo.getVenue().getAddress(),
									currentInfo.getVenue().getPostcode(),
									currentInfo.getVenue().getCountry(),
									currentInfo.getVenue().getCity(), String
											.valueOf(currentInfo.getVenue()
													.getLatitude()), String
											.valueOf(currentInfo.getVenue()
													.getLongitude()), currentInfo
											.getVenue().getPhotoURL(), String
											.valueOf(status.getId()));
							modelMain.add(venue.getModel());
							modelMain.write(System.out);
							System.out.println(filePath);
							tweetRDF.saveModel(filePath, modelMain);
							
						}
					}
					if(expandedURLlist !=null && expandedURLlist.size()!=0)
						System.out.println("text "+status.getText()+" url: "+ expandedURLlist.get(0));
					else {
						System.out.println("text "+status.getText());
					}
					TweetWithURL tweetWithURL = new TweetWithURL(status.getText(), URLlist, expandedURLlist, status.getCreatedAt());
					uRLList.add(tweetWithURL);
					System.out.println("list:");
					for(int j = 0;j<uRLList.size();j++)
					{
						System.out.println(uRLList.get(j).getText());
					}
				}

				@Override
				public void onDeletionNotice(
						StatusDeletionNotice statusDeletionNotice) {
					System.out.println("Got a status deletion notice id:"
							+ statusDeletionNotice.getStatusId());
				}

				@Override
				public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
					uRLList.clear();
					twitterStream.cleanUp();
					twitterStream.shutdown();
				}

				@Override
				public void onScrubGeo(long userId, long upToStatusId) {
					System.out.println("Got scrub_geo event userId:" + userId
							+ " upToStatusId:" + upToStatusId);
				}

				@Override
				public void onStallWarning(StallWarning warning) {
					System.out.println("Got stall warning:" + warning);
				}

				@Override
				public void onException(Exception ex) {
					ex.printStackTrace();
				}
			};
			twitterStream.addListener(listener);
			return twitterStream;
		}
		 
		 private TwitterStream initTwitterStream(String consumerKey, String consumerSecret, 
				String accessToken, String accessTokenSecret) throws Exception {
				ConfigurationBuilder cb = new ConfigurationBuilder();
				 cb.setDebugEnabled(true)
				 .setOAuthConsumerKey(consumerKey)
				 .setOAuthConsumerSecret(consumerSecret)
				 .setOAuthAccessToken(accessToken)
				 .setOAuthAccessTokenSecret(accessTokenSecret)
				 .setJSONStoreEnabled(true);
				 return (new TwitterStreamFactory(cb.build()).getInstance());
		}
	
	
	public static void main(String[] args) throws Exception {
		long id = 0;
		String username = "njy0612";
		//TODO get user long id
		GetTweetID tt_id = new GetTweetID();
		Twitter twitterConnection = null;
		try {
			twitterConnection = tt_id.init();
			id = tt_id.getUserID(twitterConnection, username);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
		TwitterStreamingForUser tt = new TwitterStreamingForUser();
		try {
			String workingDir = System.getProperty("user.dir");
			String fileName = workingDir + "/WebContent/WEB-INF/test.rdf";
			TwitterStream tws = tt.getTwitterStream(fileName);
			int count = 0;
			//from user
			long[] idToFollow = new long[1];
			idToFollow[0] = id;
			//query
			String[] stringsToTrack = new String[1];
			stringsToTrack[0] = "bababa";//"from:"+"njy0612" ;//+ " filter:links ";
			double[][] locationsToTrack = new double[0][0];
			FilterQuery myFilterQuery = new FilterQuery(count, idToFollow, stringsToTrack,
					locationsToTrack);
			myFilterQuery.track(stringsToTrack);
			tws.filter(myFilterQuery);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
	}
}
