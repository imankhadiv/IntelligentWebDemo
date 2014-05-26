package RdfModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.sun.xml.internal.rngom.parse.host.Base;

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
//			model.read(in, null);
			//model.write(System.out);
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
	public String filter(String input)
	{
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
	
	public boolean hasTweetRecord(String tweetIdStr, String fileName)
	{
		Model model = getModelFromFile(fileName);
		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "+
				"SELECT ?tweet  "+
				"WHERE { ?tweet tweet:tweetId \""+tweetIdStr+"\" ."+
				"}";

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

	public ResultSet getRecordsByAccountId(String userId,String fileName) {
		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "+
				"PREFIX person: <http://somewhere/person#> "+
				"SELECT ?name ?userId ?sceenName ?photoUrl  "+
				"WHERE { ?twitterAccount twitterAccount:userId \""+userId+"\" ."+
				"?twitterAccount twitterAccount:sceenName ?sceenName . "+
				"?twitterAccount twitterAccount:userId ?userId . " +
				"?twitterAccount twitterAccount:photoUrl ?photoUrl . " +
				"?twitterAccount twitterAccount:ownedByPerson ?person . " +
				"?person person:name ?name . " +
				"}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe.execSelect();

	}
	
	public Models.User getUserFromRecordsByTweetId(String tweetIdString, String fileName){
		
		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "+
				"PREFIX person: <http://somewhere/person#> "+
				"PREFIX tweet: <http://somewhere/tweet#> "+
				"SELECT ?name ?userId ?sceenName ?photoUrl ?liveInCity ?description "+
				"WHERE { ?tweet tweet:tweetId \""+tweetIdString+"\" ."+
				"?tweet tweet:postedByTwitterAccount ?twitterAccount . "+
				"?twitterAccount twitterAccount:sceenName ?sceenName . "+
				"?twitterAccount twitterAccount:userId ?userId . " +
				"?twitterAccount twitterAccount:photoUrl ?photoUrl . " +
				"?twitterAccount twitterAccount:liveInCity ?liveInCity . " +
				"?twitterAccount twitterAccount:description ?description . " +
				"?twitterAccount twitterAccount:ownedByPerson ?person . " +
				"?person person:name ?name . " +
				"}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		String sceenName = "";
		String userId= "";
		String photoUrl= "";
		String name= "";
		String liveInCity= "";
		String description= "";
		
		try {
			// simple select
			if (results.hasNext()) {
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
		
		
		Models.User currentUser = new Models.User(name,
				"@" + sceenName, liveInCity,
				description, photoUrl,userId);
		
		return currentUser;
	}


	
	public static void main(String[] args)
	{
		String workpathString = "/Users/nijianyue/Documents/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/IntelligentWebDemo/WEB-INF/RDF.rdf";
		BaseModel base = new BaseModel();
		System.out.println(base.hasTweetRecord("470844047595409408", workpathString));
		Models.User user = base.getUserFromRecordsByTweetId("470844047595409408", workpathString);
		System.out.println(user.getName());
	}

	public ResultSet getTweetsByAccountId(String userId,String fileName) {
		Model model = getModelFromFile(fileName);
		String postedByTwitterAccount = "http://somewhere/twitterAaccount#"+userId;
		System.out.println(postedByTwitterAccount);
		String queryString = "PREFIX tweet: <http://somewhere/tweet#> "+
				"PREFIX person: <http://somewhere/person#> "+
				"PREFIX twitterAccount: <http://somewhere/twitterAccount#> "+
				"SELECT ?tweetId ?content ?shortURL ?hasOriginTweet ?date ?hasVenue ?postedByTwitterAccount ?retweetPeople  "+
				//"WHERE { ?tweet tweet:postedByTwitterAccount \""+postedByTwitterAccount+"\" ."+
				"WHERE { ?tweet tweet:date \""+"Sat Mar 08 22:47:53 GMT 2014"+"\" ."+
				"?tweet tweet:tweetId ?tweetId . "+
				"?tweet tweet:content ?content . " +
				"?tweet tweet:shortURL ?shortURL . " +
				"?tweet tweet:hasOriginTweet ?hasOriginTweet . " +
				"?tweet tweet:date ?date . " +
				"?tweet tweet:hasVenue ?hasVenue . " +
				"?tweet tweet:postedByTwitterAccount ?postedByTwitterAccount . " +
				"?tweet tweet:retweetPeople ?retweetPeople . " +
				"?person person:name ?name . " +
				"}";
		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe.execSelect();
	}

}
