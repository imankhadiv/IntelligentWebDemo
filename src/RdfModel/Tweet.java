package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Tweet extends BaseModel {

	private Model model;
	private final String URI = "http://somewhere";
	private Property tweetId;
	private Property content;
	private Property shortUrl;
	private Property date;
	private Property hasVenue;
	private Property postedByTwitterAccount;
	private Property hasOriginTweet;
	private Property retweetPeople;

	public Tweet() {
		String tweetURI = URI + "/tweet#";
		model = ModelFactory.createDefaultModel();

		tweetId = model.createProperty(tweetURI, "tweetId");
		content = model.createProperty(tweetURI, "content");
		shortUrl = model.createProperty(tweetURI, "shortUrl");
		date = model.createProperty(tweetURI, "date");
		hasVenue = model.createProperty(tweetURI, "hasVenue");
		postedByTwitterAccount = model.createProperty(tweetURI,
				"postedByTwitterAccount");
		hasOriginTweet = model.createProperty(tweetURI, "hasOriginTweet");
		retweetPeople = model.createProperty(tweetURI, "retweetPeople");
	}

	public void saveTweet(String tweetIdStr, String contentStr,
			String shortUrlStr, String dateStr, String venueName,
			String retweetId, String userId) {
		String tweetURL = URI + "/tweet#" + tweetIdStr;
		// source for twitterAccount
		String twitterAccountURL = URI + "/twitterAccount#" + userId;
		// Resource twitterAccount = model.createResource(tweetURL);
		Resource twitterAccount = model.createResource(twitterAccountURL);
		// source for venue
		String VenueURL = URI + "/venue#" + venueName;
		Resource venue = model.createResource(VenueURL);
		// source for originTweet
		String originTweetURL = URI + "/tweet#" + retweetId;
		Resource originTweet = model.createResource(originTweetURL);

		if (model.getResource(tweetURL) != null) {
			model.getResource(tweetURL).addProperty(tweetId, tweetIdStr)
					.addProperty(content, contentStr)
					.addProperty(shortUrl, shortUrlStr)
					.addProperty(hasVenue, venue)
					.addProperty(postedByTwitterAccount, twitterAccount)
					.addProperty(hasOriginTweet, originTweet);
		} else {
			model.createResource(tweetURL).addProperty(tweetId, tweetIdStr)
					.addProperty(content, contentStr)
					.addProperty(shortUrl, shortUrlStr)
					.addProperty(hasVenue, venue)
					.addProperty(postedByTwitterAccount, twitterAccount)
					.addProperty(hasOriginTweet, originTweet);
		}

	}
	/**
	 * 
	 * @param tweetIdStr
	 * @param contentStr
	 * @param shortUrlStr
	 * @param dateStr
	 * @param retweetId
	 * @param userId
	 */

	public void saveTweet(String tweetIdStr, String contentStr,
			String shortUrlStr, String dateStr, String retweetId, String userId) {

		String tweetURL = URI + "/tweet#" + tweetIdStr;
		// source for twitterAccount
		String twitterAccountURL = URI + "/twitterAccount#" + userId;
		Resource twitterAccount = model.createResource(twitterAccountURL);
		// source for originTweet
		String originTweetURL = URI + "/tweet#" + retweetId;
		Resource originTweet = model.createResource(originTweetURL);
		if (retweetId != null) {
			if (model.getResource(tweetURL) != null) {
				model.getResource(tweetURL).addProperty(tweetId, tweetIdStr)
						.addProperty(content, contentStr)
						.addProperty(shortUrl, shortUrlStr)
						.addProperty(postedByTwitterAccount, twitterAccount)
						.addProperty(hasOriginTweet, originTweet);
			} else {
				model.createResource(tweetURL).addProperty(tweetId, tweetIdStr)
						.addProperty(content, contentStr)
						.addProperty(shortUrl, shortUrlStr)
						.addProperty(postedByTwitterAccount, twitterAccount)
						.addProperty(hasOriginTweet, originTweet);
			}
		} else {
			if (model.getResource(tweetURL) != null) {
				model.getResource(tweetURL).
						addProperty(tweetId, tweetIdStr)
						.addProperty(content, contentStr)
						.addProperty(shortUrl, shortUrlStr)
						.addProperty(postedByTwitterAccount, twitterAccount)
						.addProperty(this.date, "N/A")
						.addProperty(this.hasOriginTweet, "N/A")
						.addProperty(this.retweetPeople, "N/A")
						.addProperty(this.hasVenue, "N/A");
			} else {
				model.createResource(tweetURL).
						addProperty(tweetId, tweetIdStr)
						.addProperty(content, contentStr)
						.addProperty(shortUrl, shortUrlStr)
						.addProperty(postedByTwitterAccount, twitterAccount)
						.addProperty(this.date, "N/A")
						.addProperty(this.hasOriginTweet, "N/A")
						.addProperty(this.retweetPeople, "N/A")
						.addProperty(this.hasVenue, "N/A");
			}
		}

	}

	/**
	 * 
	 * @param tweetIdStr
	 * @param contentStr
	 * @param dateStr
	 * @param retweetId
	 * @param userId
	 */
	public void saveTweet(String tweetIdStr, String contentStr, String dateStr,
			String retweetId, String userId) {
		String tweetURL = URI + "/tweet#" + tweetIdStr;
		// source for twitterAccount
		String twitterAccountURL = URI + "/twitterAccount#" + userId;
		Resource twitterAccount = model.createResource(twitterAccountURL);
		// source for originTweet
		String originTweetURL = URI + "/tweet#" + retweetId;
		Resource originTweet = model.createResource(originTweetURL);

		if (model.getResource(tweetURL) != null) {
			model.getResource(tweetURL).addProperty(tweetId, tweetIdStr)
					.addProperty(content, contentStr)
					.addProperty(postedByTwitterAccount, twitterAccount)
					.addProperty(hasOriginTweet, originTweet)
					.addProperty(this.date, "N/A")
					.addProperty(this.shortUrl, "N/A")
					.addProperty(this.retweetPeople, "N/A")
					.addProperty(this.hasVenue, "N/A");
		} else {
			model.createResource(tweetURL).addProperty(tweetId, tweetIdStr)
					.addProperty(content, contentStr)
					.addProperty(postedByTwitterAccount, twitterAccount)
					.addProperty(hasOriginTweet, originTweet)
					.addProperty(this.date, "N/A")
					.addProperty(this.shortUrl, "N/A")
					.addProperty(this.retweetPeople, "N/A")
					.addProperty(this.hasVenue, "N/A");
		}
	}

	/**
	 * 
	 * @param accountId
	 * @param tweetId
	 * @param text
	 * @param date
	 * @param shortURL
	 */
	public void saveTweet2(String accountId, String tweetId, String text,
			String date, String shortURL) {
		String tweetURI = URI + "/tweet#" + tweetId;
		String twitterAccountURI = URI + "/twitterAaccount#" + accountId;
		Resource twitterAcccount = model.createResource(twitterAccountURI);

		model.createResource(tweetURI).addProperty(this.tweetId, tweetId)
				.addProperty(this.content, text).addProperty(this.date, date)
				.addProperty(this.shortUrl, shortURL)
				.addProperty(this.postedByTwitterAccount, twitterAcccount)
				.addProperty(this.hasVenue, "N/A")
				.addProperty(this.hasOriginTweet, "N/A")
				.addProperty(this.retweetPeople, "N/A");

	}

	/**
	 * 
	 * @param accountId
	 * @param tweetId
	 * @param text
	 * @param date
	 * @param shortURL
	 * @param retweetPeople
	 */
	public void saveTweet2(String accountId, String tweetId, String text,
			String date, String shortURL, String retweetPeople) {
		String tweetURI = URI + "/tweet#" + tweetId;
		String twitterAccountURI = URI + "/twitterAaccount#" + accountId;
		Resource twitterAcccount = model.createResource(twitterAccountURI);

		model.createResource(tweetURI).addProperty(this.tweetId, tweetId)
				.addProperty(this.content, text).addProperty(this.date, date)
				.addProperty(this.shortUrl, shortURL)
				.addProperty(this.postedByTwitterAccount, twitterAcccount)
				.addProperty(this.retweetPeople, retweetPeople)
				.addProperty(this.hasOriginTweet, "N/A")
				.addProperty(this.hasVenue, "N/A");

	}

	public Model getModel() {
		return model;
	}

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/RDF.rdf";
		Tweet tweet = new Tweet();
		Model modelMain = tweet.getModelFromFile(fileName);
		System.out.println("..............///////");
		modelMain.write(System.out);

		tweet.saveTweet("123", "!23", "!23", "123", "123", "123");
		modelMain.add(tweet.getModel());
		tweet.saveModel(fileName, modelMain);

	}
}
