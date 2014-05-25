package RdfModel;

import java.io.IOException;
import java.io.InputStream;

import Models.TweetWithURL;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class RDFQuery {
	
	public TweetWithURL getTweetWithURL(String tweetId)
	{
		TweetWithURL tweetWithURL = null;// new TweetWithURL(_text, _displayURL, _expandedURL, _createdAt);
		return tweetWithURL;
	}
	
	public static void main(String[] args) throws IOException
	{
		String userId = "864982249";
		String screenname = "Sheffieldis";
		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/RDF.rdf";
		InputStream in = FileManager.get().open(fileName);
		Model model = ModelFactory.createDefaultModel();
		model.read(in,null);
		in.close();
		
		String queryString = "PREFIX twitter: <http://somewhere/twitter#> "+
				"PREFIX person: <http://somewhere/person#> "+
				"SELECT ?name ?userId ?sceenName ?photoUrl  "+
				"WHERE { ?twitter twitter:userId \""+userId+"\" ."+
				"?twitter twitter:sceenName \""+screenname+"\" . "+
				"?twitter twitter:sceenName ?sceenName . "+
				"?twitter twitter:userId ?userId . " +
				"?twitter twitter:photoUrl ?photoUrl . " +
				"?twitter twitter:ownedByPerson ?person . " +
				"?person person:name ?name . " +
				"}";
		
		 com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
	    	
    	 // Execute the query and obtain results
    	 QueryExecution qe = QueryExecutionFactory.create(query, model);
    	 ResultSet results = qe.execSelect();
		
		try {
			// simple select
			if(results.hasNext()){
				QuerySolution qs = results.next();
				System.out.println(qs.getLiteral("?sceenName"));
				System.out.println(qs.getLiteral("?userId"));
				System.out.println(qs.getLiteral("?photoUrl"));
				System.out.println(qs.getLiteral("?name"));
				
			}
		} finally {
			qe.close();
		}
	}
}
