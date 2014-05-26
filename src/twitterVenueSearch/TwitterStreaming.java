package twitterVenueSearch;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.TweetWithURL;
import Models.User;
import RdfModel.Person;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;

import com.hp.hpl.jena.rdf.model.Model;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterStreaming {
	 private String consumerkey = "5aUFVk9PfsdKQusWkwiOOQ";	
	 private String consumersecret = "17Ze1q8mYSRFPgFGV6sBJrybYUrjpMYR6JmP29xvNKE";	
	 private String accesstoken = "2365765400-PzPlz6uUcHZsDdDwbozJpvfl4CxkC4mKSzyfuCQ";	
	 private String accesstokensecret = "DYTINSWrKwjoelp3nmBRUlzuD0EDloauLX1HGyXOtYDvT";	
//	 private MyDB db;
	 private List<User> userList;
	 
	 
	 public List<User> getUserList() {
		return userList;
	}


	public void setuRLList(List<User> uRLList) {
		this.userList = uRLList;
	}
	 
	 public TwitterStreaming(){
		 userList = new ArrayList<User>();
//		 db=new MyDB();
	 }
	 TwitterStream getTwitterStream(final String filePath) throws Exception {
		final TwitterStream twitterStream = initTwitterStream(consumerkey,
				consumersecret, accesstoken, accesstokensecret);
		StatusListener listener = new StatusListener() {
			@Override
			public void onStatus(Status status) {
				twitter4j.User user=status.getUser();
				Tweet tweetRDF = new Tweet();
				Model modelMain = tweetRDF.getModelFromFile(filePath);
				if(status.getRetweetedStatus()!=null)
				{
					tweetRDF.saveTweet(String.valueOf(status.getId()), status
							.getText(), status
							.getCreatedAt().toString(), String
							.valueOf(status.getRetweetedStatus().getId()),
							String.valueOf(status.getUser().getId()));
				}
				else {
					tweetRDF.saveTweet(String.valueOf(status.getId()), status
							.getText(), status
							.getCreatedAt().toString(), null,
							String.valueOf(status));
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
						user.getId());
				modelMain.add(person.getModel());
				modelMain.write(System.out);
				tweetRDF.saveModel(filePath, modelMain);
//					db.insert(user.getName(), user.getScreenName(),user.getLocation(),user.getDescription(),user.getProfileImageURL());
				
				System.out.println("@" + status.getUser().getScreenName()
						+ " - " + status.getText()+" id: "+status.getId());
				User userToSave = new User(user.getName(), "@"+user.getScreenName() , user.getLocation(), user.getDescription(), user.getProfileImageURL(), String.valueOf(user.getId()));
				userList.add(userToSave);
			}

			@Override
			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:"
						+ statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				
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
		TwitterStreaming tt = new TwitterStreaming();
		String filePath ="";
		
		try {
			TwitterStream tws = tt.getTwitterStream(filePath);
			int range=1;
			int count = 0;
			long[] idToFollow = new long[0];
			//query
			String[] stringsToTrack = new String[0];
			//date
			double[][] locationsToTrack ={{(-1.4782666-1*range), (53.3737444-1*range)}, {(-1.4782666+1*range), (53.3737444+1*range)}}; 
		
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
