package RdfModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import Models.TweetWithURL;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
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
			System.out.println("inside file");
			throw new IllegalArgumentException("File: " + fileName
					+ "Not found");
		} else {
			Model model = ModelFactory.createDefaultModel();
			model.read(in, null);
			// model.write(System.out);

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
	public ResultSet getRecordsByScreenName(String screenName, String fileName) {

		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "SELECT ?name ?userId ?sceenName ?photoUrl  "
				+ "WHERE { ?twitterAccount twitterAccount:sceenName \""
				+ screenName
				+ "\" ."
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe.execSelect();

	}
	
	public void getallTweets(String fileName)
	{
		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?tweet ?content ?tweetId"
				+ "WHERE { ?tweet tweet:content ?content . "
				+ "?tweet tweet:content ?content . "
				+ "}";
		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		try {
			// simple select
			if (results.hasNext()) {
				ResultSetFormatter.out(System.out, results, query) ;
			}
		} finally {
			qe.close();
		}
			
	}
	
	
	public TweetWithURL getTweetWithURLRecordsByScreenName(String screenName, String fileName) {

		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
//				+ "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?twitterAccount ?userId "
				+ "WHERE { ?twitterAccount twitterAccount:sceenName \""
				+ screenName
				+ "\" ."
				+ "?twitterAccount twitterAccount:userId ?userId . "
//				+ "?tweet tweet:postedByTwitterAccount \"http://somewhere/twitterAccount#?userId\" . "
//				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "}";

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
				
				QuerySolution qs = results.next();
				userId = qs.getLiteral("?userId").getString();
				queryString =  "PREFIX tweet: <http://somewhere/tweet#> "
						+ "SELECT ?twitterAccount ?userId "
						+ "WHERE { ?twitterAccount twitterAccount:sceenName \""
						+ screenName
						+ "\" ."
						+ "?twitterAccount twitterAccount:userId ?userId . "
						+ "?tweet tweet:postedByTwitterAccount \"http://somewhere/twitterAccount#?"+userId+"\" . "
//						+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
						+ "}";
			}
		} finally {
			qe.close();
		}

		TweetWithURL currentTweet = null;// new TweetWithURL(_text, _displayURL, _expandedURL, _createdAt)
		return currentTweet;

	}
	

	public boolean hasTweetRecord(String tweetIdStr, String fileName) {
		Model model = getModelFromFile(fileName);
		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?tweet  " + "WHERE { ?tweet tweet:tweetId \""
				+ tweetIdStr + "\" ." + "}";

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

	public ResultSet getRecordsByAccountId(String userId, String fileName) {
		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "SELECT ?name ?userId ?sceenName ?photoUrl  "
				+ "WHERE { ?twitterAccount twitterAccount:userId \""
				+ userId
				+ "\" ."
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe.execSelect();

	}

	public Models.User getUserFromRecordsByTweetId(String tweetIdString,
			String fileName) {

		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "PREFIX tweet: <http://somewhere/tweet#> "
				+ "SELECT ?tweet ?twitterAccount ?person ?name ?userId ?sceenName ?photoUrl ?liveInCity ?description "
//				+ "SELECT ?tweet ?twitterAccount ?person ?name ?userId ?sceenName ?photoUrl ?liveInCity ?description "
				+ "WHERE { ?tweet  tweet:tweetId \""+ tweetIdString+ "\" ."
				+ "?tweet tweet:postedByTwitterAccount ?twitterAccount . "
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:description ?description . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . " 
				+ "?person person:liveInCity ?liveInCity . "
				+ "}";

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
//				ResultSetFormatter.out(System.out, results, query) ;
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


//	public ResultSet getTweetsByAccountId(String userId, String fileName) {
//		Model model = getModelFromFile(fileName);
////		String postedByTwitterAccount = "http://somewhere/twitterAaccount#"
////				+ userId;
////
//		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "
////				 +
////				 "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
//				//+ "SELECT ?tweetId ?content ?shortURL ?hasOriginTweet ?date ?hasVenue ?postedByTwitterAccount ?retweetPeople  "
//				+ "SELECT * "
//				+ "WEHERE {"
//				//+"?tweet tweet:tweetId ?tweetId . "
//
////				+ "WHERE { ?tweet tweet:tweetId \""
////				+ userId
////				+ "\" ."
//				//?tweet tweet:tweetId ?tweetId . "
//				+ " ?tweet ."
//				//+ "?tweet tweet:content ?content . "
////				+ "?tweet tweet:shortURL ?shortURL . "
////				+ "?tweet tweet:hasOriginTweet ?hasOriginTweet . "
////				+ "?tweet tweet:date ?date . "
////				+ "?tweet tweet:hasVenue ?hasVenue . "
////				+ "?tweet tweet:retweetPeople ?retweetPeople . "
////				+ "?tweet tweet:postedByTwitterAccount ?postedByTwitterAccount . "
//				+ "}";
//		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
//		// Execute the query and obtain results
//		QueryExecution qe = QueryExecutionFactory.create(query, model);
//		return qe.execSelect();
//	}

	
	public static void main(String[] args) {
		String workpathString = "/Users/nijianyue/Documents/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/IntelligentWebDemo/WEB-INF/RDF.rdf";
		BaseModel base = new BaseModel();
//		System.out.println(base.hasTweetRecord("470664099454783488", workpathString));
//		Models.User user = base.getUserFromRecordsByTweetId("470664099454783488", workpathString);
//		System.out.println(user.getName());
//		base.getTweetWithURLRecordsByScreenName("njy0612", workpathString);
		base.getallTweets(workpathString);
	}


}
