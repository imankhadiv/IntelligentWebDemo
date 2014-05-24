package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Tweet {
	
	private Model model;
	private final String URI = "http://somewhere";
	private Property tweetId;
	private Property content;
	private Property shortUrl;
	private Property date;
	private Property hasVenue;
	private Property postedByTwitterAccount;
	private Property hasOriginTweet;
	
	public Tweet(){
		String tweetURI = URI + "/tweet#";
		model = ModelFactory.createDefaultModel();
		
		tweetId = model.createProperty(tweetURI, "tweetId");
		content = model.createProperty(tweetURI, "content");
		shortUrl = model.createProperty(tweetURI, "shortUrl");
		date = model.createProperty(tweetURI, "date");
		hasVenue = model.createProperty(tweetURI, "hasVenue");
		postedByTwitterAccount = model.createProperty(tweetURI, "postedByTwitterAccount");
		hasOriginTweet = model.createProperty(tweetURI, "hasOriginTweet");
	}
	
	public void saveTweet(String tweetIdStr, String contentStr,String shortUrlStr,String dateStr
			,String venueName,String accountId, String retweetId, String userId){
		String tweetURL = URI +"/tweet#" + tweetId;
		// source for twitterAccount
		String twitterAccountURL = URI + "/twitterAccount#" + userId;
		Resource twitterAccount = model.createResource(tweetURL);
		// source for venue
		String VenueURL = URI + "/venue#" + venueName;
		Resource venue = model.createResource(VenueURL);
		// source for originTweet
		String originTweetURL = URI + "/tweet#" + retweetId;
		Resource originTweet = model.createResource(originTweetURL);
		
		model.createResource(tweetURL)
			.addProperty(tweetId, tweetIdStr)
			.addProperty(content, contentStr)
			.addProperty(shortUrl, shortUrlStr)
			.addProperty(hasVenue, venue)
			.addProperty(postedByTwitterAccount, twitterAccount)
			.addProperty(hasOriginTweet, originTweet);
	}
	
	
	public Model getModel()
	{
		return model;
	}
	

}
