package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class TwitterAccount extends BaseModel{
	private final String URI = "http://somewhere";
	private Model model;
	private Property propUserId;
	private Property propScreenName;
	private Property propDescription;
	private Property propUserPhotoUrl;
	private Property propOwnedByPerson;
	
	
	public TwitterAccount(){
		
		String twitterAccountURI =URI+"/twitterAccount#";
		model = ModelFactory.createDefaultModel();
		
		//create schema
		propUserId = model.createProperty(twitterAccountURI, "userId");
		propScreenName = model.createProperty(twitterAccountURI, "sceenName");
		propDescription = model.createProperty(twitterAccountURI, "description");
		propUserPhotoUrl = model.createProperty(twitterAccountURI, "photoUrl");
		propOwnedByPerson = model.createProperty(twitterAccountURI, "ownedByPerson");

	}
	/**
	 * 
	 * @param name
	 * @param userId
	 * @param ScreenName
	 * @param description
	 * @param userPhotoUrl
	 */
	public void saveTwitterAccount(String name, String userId, String ScreenName, String description, String userPhotoUrl){
		String twitterAccountURL =  URI+"/twitterAccount#" + userId;
		// get source for person
		String personURI = URI+"/person#" + this.filter(name);
		Resource person = model.createResource(personURI);
			model.createResource(twitterAccountURL)
			.addProperty(propUserId, userId)
			.addProperty(propScreenName, ScreenName)
			.addProperty(propDescription, description).addProperty(propUserPhotoUrl, userPhotoUrl).addProperty(propOwnedByPerson, person);
		

	}
	
	public Model getModel(){
		return model;
	}
	
}
