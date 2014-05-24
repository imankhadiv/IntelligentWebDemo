package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

public class Tweet extends BaseModel{

	
		private final String URI = "http://somewhere";
		private Model model;
		
		//private Property propAccountId;
		private Property propTweetId;
		private Property propText;
		private Property propDate;
		private Property propOwnByTwitterAccount;
		private Property propHasVenue;
		private Property propShortURL;
		
		public Tweet(){
			
			String tweetURI =URI+"/tweet#";
			model = ModelFactory.createDefaultModel();
			
			//create schema
			propTweetId = model.createProperty(tweetURI,"tweetID");
			propOwnByTwitterAccount = model.createProperty(tweetURI,"ownByTwitterAccount");//AccountId
			propText = model.createProperty(tweetURI,"text");
			propDate = model.createProperty(tweetURI,"date");
			propHasVenue = model.createProperty(tweetURI,"hasVenue");
			propShortURL = model.createProperty(tweetURI,"shortURL");

		}
		
		public void saveTweet(String accountId,String tweetId, String text, String date, String venue, String shortURL){
			String tweetURI =  URI+"/tweet#" + tweetId;
			String twitterAccountURI = URI+"/twitter_account#";
			model.createResource(tweetURI)
			.addProperty(propTweetId, tweetId)
			.addProperty(propText, text)
			.addProperty(propDate, date)
			.addProperty(propHasVenue, venue)
			.addProperty(propShortURL, shortURL)
			.addProperty(propOwnByTwitterAccount, twitterAccountURI);
		}
		
		public Model getModel(){
			return model;
		}
		
}
