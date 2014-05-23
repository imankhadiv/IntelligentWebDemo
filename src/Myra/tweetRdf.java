package Myra;

import java.util.Date;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class tweetRdf {
	
	Model model;
	Property propTweetId;
	Property propContent;
	Property propShortUrl;
	Property propDate;
	Property propHasVenue;
	Property propPostedByTwitterAccount;

	
	public void tweetRdf (){
		
		String tweetURI = "http://somewhere/tweet";
		model = ModelFactory.createDefaultModel();
		
		//create schema
		propTweetId = model.createProperty(tweetURI, "tweetId");
		propContent = model.createProperty(tweetURI, "content");
		propShortUrl = model.createProperty(tweetURI, "shortUrl");
		propDate = model.createProperty(tweetURI, "date");
		propHasVenue = model.createProperty(tweetURI, "hasVenue");
		propPostedByTwitterAccount = model.createProperty(tweetURI, "postedByTwitterAccount");
	}
	
	public void saveTweetRdf(String tweetId, String content, String shortUrl, String date, String hasVenue, String postedByTwitterAccount){
		//Create Resource and add property
		Resource newTweet = model.createResource().addProperty(propTweetId, tweetId).addProperty(propContent, content)
		.addProperty(propShortUrl, shortUrl).addProperty(propDate, date).addProperty(propHasVenue, hasVenue)
		.addProperty(propPostedByTwitterAccount, postedByTwitterAccount);
	}
   
	public Model getModel(){
		return model;
	}
}
