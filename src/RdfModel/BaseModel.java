package RdfModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import twitter.FrequentKeywords;
import Models.TweetWithURL;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class BaseModel {

	/**
	 * This method is implemented for getting the model from rdf file inside
	 * servlet
	 * 
	 * @param fileName
	 * @return
	 */
	public Model getModelFromFile(String fileName) {
		InputStream in = FileManager.get().open(fileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + fileName
					+ "Not found");
		} else {
			Model model = ModelFactory.createDefaultModel();
			model.read(in, null);

			return model;
		}
	}

	/**
	 * This method is implemented for saving the model in rdf file inside
	 * servlet
	 * 
	 * @param fileName
	 * @param model
	 */
	public void saveModel(String fileName, Model model) {

		FileWriter out = null;
		try {
			out = new FileWriter(fileName);
			model.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public String filter(String input) {
		return input.replace("#", "").trim();
	}

	/**
	 * 
	 * @param sreenName
	 * @param fileName
	 * @return
	 */
	public beans.Person getRecordsByScreenName(String screenName,
			String fileName) {

		Model model = getModelFromFile(fileName);
		beans.Person person = new beans.Person();

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "SELECT ?name ?userId ?sceenName ?photoUrl  "
				+ "WHERE { ?twitterAccount twitterAccount:sceenName \""
				+ screenName
				+ "\" ."
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:description ?description . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		try {
			// simple select
			if (results.hasNext()) {
				QuerySolution qs = results.next();
				person.setName(String.valueOf(qs.getLiteral("?name")));
				person.setScreenName(String.valueOf(qs.getLiteral("?sceenName")));
				person.setUsrId(String.valueOf(qs.getLiteral("?userId")));
				person.setProfilePicture(String.valueOf(qs
						.getLiteral("?photoUrl")));
				person.setDescription(String.valueOf(qs
						.getLiteral("?description")));

			}
		} finally {
			// qe.close();
		}
		return person;

	}

	/**
	 * 
	 * @param userId
	 * @param fileName
	 * @return
	 */
	public beans.Person getallTweets(String userId, String fileName) {
		beans.Person person = getRecordsByAccountId(userId, fileName);
		Model model = getModelFromFile(fileName);
		StringBuilder sb = new StringBuilder();
		String twitterAccount = "http://somewhere/twitterAaccount#"
				+ person.getUsrId();
		ArrayList<beans.Tweet> tweetList = new ArrayList<beans.Tweet>();

		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT *"
				+ "WHERE { "
				+ "?tweet tweet:content ?content . "
				+ "?tweet tweet:tweetId ?tweetId . "
				+ "?tweet tweet:shortUrl ?shortUrl . "
				+ "?tweet tweet:hasOriginTweet ?hasOriginTweet . "
				+ "?tweet tweet:date ?date . "
				+ "?tweet tweet:hasVenue ?hasVenue . "
				+ "?tweet tweet:retweetPeople ?retweetPeople . "
				+ "?tweet tweet:postedByTwitterAccount ?postedByTwitterAccount . "
				+ "}";
		System.out.println(queryString);
		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		try {
			// simple select
			while (results.hasNext()) {
				QuerySolution qs = results.next();
				if (twitterAccount.equals(String.valueOf(qs
						.getResource("?postedByTwitterAccount")))) {
					beans.Tweet tweets = new beans.Tweet();
					tweets.setTweetId(String.valueOf(qs.getLiteral("?tweetId")));
					tweets.setContent(String.valueOf(qs.getLiteral("?content")));
					tweets.setShortUrl(String.valueOf(qs
							.getLiteral("?shortUrl")));
					tweets.setHasOriginalTweet(String.valueOf(qs
							.getLiteral("?hasOriginTweet")));
					tweets.setDate(String.valueOf(qs.getLiteral("?date")));
					tweets.setHasVenue(String.valueOf(qs
							.getLiteral("?hasVenue")));
					tweets.setRetweetPeople(String.valueOf(qs
							.getLiteral("?retweetPeople")));
					tweetList.add(tweets);
					sb.append(tweets.getContent()+" ");

				}

			}
		} finally {
			qe.close();
		}
		person.setTweets(tweetList);
		person.setMap(FrequentKeywords.getKeywords(sb.toString()));//getting keywords from all tweets
		return person;

	}
	
	
	public List<TweetWithURL> getTweetWithURLRecordsByScreenName(String screenName, String fileName) {

		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				// + "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?twitterAccount ?userId "
				+ "WHERE { ?twitterAccount twitterAccount:sceenName \""
				+ screenName
				+ "\" ."
				+ "?twitterAccount twitterAccount:userId ?userId . "
				// +
				// "?tweet tweet:postedByTwitterAccount \"http://somewhere/twitterAccount#?userId\" . "
				// + "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "}";
		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		String userId = "";
		List<TweetWithURL> currentTweet = null;
		try {
			// simple select
			if (results.hasNext()) {
//				ResultSetFormatter.out(System.out, results, query) ;
				QuerySolution qs = results.next();
				userId = qs.getLiteral("?userId").getString();
				currentTweet = getTweetsRecordByAccountId(fileName,userId);
			}
		} finally {
			qe.close();
		}

//		System.out.println(currentTweet.size());
		return currentTweet;

	}
	
	public List<TweetWithURL> getTweetsRecordByAccountId(String fileName, String userId)
	{
		Model model = getModelFromFile(fileName);
		List<TweetWithURL> list = new ArrayList<TweetWithURL>();
		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?content ?date ?shortUrl "// ?tweet ?content "
				+ "WHERE { "
				+ "?twitterAccount twitterAccount:userId \""
						+ userId
						+ "\" . "
				+ "?tweet tweet:postedByTwitterAccount <http://somewhere/twitterAccount#2365765400> . "
				+ "?tweet tweet:content ?content . "
				+ "?tweet tweet:date ?date . "
				+ "?tweet tweet:shortUrl ?shortUrl . "
				+ "}";
		System.out.println(queryString);
		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		try {
			// simple select
			if (results.hasNext()) {
//				ResultSetFormatter.out(System.out, results, query) ;
				QuerySolution qs = results.next();
				System.out.println(qs.getLiteral("?content"));
				String content = "";
				content = qs.getLiteral("?content").getString();
				
				System.out.println(qs.getLiteral("?date"));
				String date = "";
				date = qs.getLiteral("?date").getString();
				
				System.out.println(qs.getLiteral("?shortUrl"));
				String shortUrl = "";
				shortUrl = qs.getLiteral("?shortUrl").getString();
				List<String> urlList = new ArrayList<String>();
				urlList.add(shortUrl);
				TweetWithURL currentTweet = new TweetWithURL(content, urlList, urlList, date);
				list.add(currentTweet);
			}
		} finally {
			qe.close();
		}
		
		return list;
	}
	

	public boolean hasTweetRecord(String tweetIdStr, String fileName) {
		Model model = getModelFromFile(fileName);
		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?tweet  " + "WHERE { ?tweet tweet:tweetId \""
				+ tweetIdStr + "\" . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet rs = qe.execSelect();
		return rs.hasNext();
	}

	/**
	 * 
	 * @param userId
	 * @param fileName
	 * @return
	 */

	public beans.Person getRecordsByAccountId(String userId, String fileName) {
		Model model = getModelFromFile(fileName);
		beans.Person person = new beans.Person();

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "SELECT ?name ?userId ?sceenName ?photoUrl  "
				+ "WHERE { ?twitterAccount twitterAccount:userId \""
				+ userId
				+ "\" ."
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:description ?description . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		try {
			// simple select
			if (results.hasNext()) {
				QuerySolution qs = results.next();
				person.setName(String.valueOf(qs.getLiteral("?name")));
				person.setScreenName(String.valueOf(qs.getLiteral("?sceenName")));
				person.setUsrId(String.valueOf(qs.getLiteral("?userId")));
				person.setProfilePicture(String.valueOf(qs
						.getLiteral("?photoUrl")));
				person.setDescription(String.valueOf(qs
						.getLiteral("?description")));

			}
		} finally {
			// qe.close();
		}
		return person;

	}

	public Models.User getUserFromRecordsByTweetId(String tweetIdString,
			String fileName) {

		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?tweet ?twitterAccount ?person ?name ?userId ?sceenName ?photoUrl ?liveInCity ?description "
				// +
				// "SELECT ?tweet ?twitterAccount ?person ?name ?userId ?sceenName ?photoUrl ?liveInCity ?description "
				+ "WHERE { ?tweet  tweet:tweetId \""
				+ tweetIdString
				+ "\" ."
				+ "?tweet tweet:postedByTwitterAccount ?twitterAccount . "
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:description ?description . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . "
				+ "?person person:liveInCity ?liveInCity . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		String sceenName = "";
		String userId = "";
		String photoUrl = "";
		String name = "";
		String liveInCity = "";
		String description = "";

		try {
			// simple select
			if (results.hasNext()) {
				// ResultSetFormatter.out(System.out, results, query) ;
				QuerySolution qs = results.next();
				System.out.println(qs.getLiteral("?sceenName"));
				sceenName = qs.getLiteral("?sceenName").getString();
				System.out.println(qs.getLiteral("?userId"));
				userId = qs.getLiteral("?userId").getString();
				System.out.println(qs.getLiteral("?photoUrl"));
				photoUrl = qs.getLiteral("?photoUrl").getString();
				System.out.println(qs.getLiteral("?name"));
				name = qs.getLiteral("?name").getString();
				System.out.println(qs.getLiteral("?liveInCity"));
				liveInCity = qs.getLiteral("?liveInCity").getString();
				System.out.println(qs.getLiteral("?description"));
				description = qs.getLiteral("?description").getString();
			}
		} finally {
			qe.close();
		}

		Models.User currentUser = new Models.User(name, "@" + sceenName,
				liveInCity, description, photoUrl, userId);
		return currentUser;
	}

	public static void main(String[] args) {
		String workpathString = "/Users/nijianyue/Documents/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/IntelligentWebDemo/WEB-INF/RDF.rdf";
		BaseModel base = new BaseModel();

//		System.out.println(base.hasTweetRecord("470664099454783488", workpathString));
//		Models.User user = base.getUserFromRecordsByTweetId("470664099454783488", workpathString);
//		System.out.println(user.getName());
		base.getTweetWithURLRecordsByScreenName("njy0612", workpathString);
//		base.getallTweets(workpathString);
//		base.getTweetsRecordByAccountId(workpathString, "21203769");

	}

}
