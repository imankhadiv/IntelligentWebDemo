package test;


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class FoursquareAccountRdf {
	
	static Model model;
	Property propUserId;
	Property propUserPhotoUrl;
	Property propDescription;
	Property propOwnedByPerson;
	
	public FoursquareAccountRdf(){
		
		String foursquareURI= "http://somewhere/foursquare";
		model = ModelFactory.createDefaultModel();
		
		//create schema
		propUserId = model.createProperty(foursquareURI, "userId");
		propUserPhotoUrl = model.createProperty(foursquareURI, "userPhotoUrl");
		propDescription = model.createProperty(foursquareURI, "description");
		propOwnedByPerson = model.createProperty(foursquareURI, "ownedByPerson");
	}
	
	public void saveFoursquareAccount(String userId, String userPhotoUrl,String description,String ownedByPerson)
	{
		Resource newFoursquareAccount = model.createResource().addProperty(propUserId, userId).addProperty(propUserPhotoUrl, userPhotoUrl)
		.addProperty(propDescription, description).addProperty(propOwnedByPerson, ownedByPerson);
		
		/*File file = new File("C:/Users/Mira/workspace/IntelligentWeb/src/ontology/foursquareFile.rdf");
		try {
			FileWriter writer = new FileWriter(file);
			model.write(writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public Model getModel()
	{
		return model;
	}
}
