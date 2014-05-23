package Myra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class twitterAccountRdf {

	Model model;
	Property propUserId;
	Property propScreenName;
	Property propDescription;
	Property propUserPhotoUrl;
	Property propOwnedByPerson;
	
	
	public twitterAccountRdf(){
		
		String twitterAccountURI ="http://somewhere/twitter";
		model = ModelFactory.createDefaultModel();
		
		//create schema
		propUserId = model.createProperty(twitterAccountURI, "userId");
		propScreenName = model.createProperty(twitterAccountURI, "sceenName");
		propDescription = model.createProperty(twitterAccountURI, "description");
		propUserPhotoUrl = model.createProperty(twitterAccountURI, "photoUrl");
		propOwnedByPerson = model.createProperty(twitterAccountURI, "ownedByPerson");

	}
	
	public void saveTwitterAccount(String userId, String ScreenName, String description, String userPhotoUrl,String ownedByUser){
		Resource newTwitterAccount = model.createResource().addProperty(propUserId, userId).addProperty(propScreenName, ScreenName)
		.addProperty(propDescription, description).addProperty(propUserPhotoUrl, userPhotoUrl).addProperty(propOwnedByPerson, ownedByUser);
		
	}
	
	public Model getModel(){
		return model;
	}
}
