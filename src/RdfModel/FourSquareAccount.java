package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class FourSquareAccount extends BaseModel {
	private final String URI = "http://somewhere";
	private Model model;
	private Property userId;
	private Property photoUrl;
	private Property ownedByPerson;

	public FourSquareAccount() {
		String fourSquareAccountURI = URI + "/4sqAccount#";
		model = ModelFactory.createDefaultModel();
		userId = model.createProperty(fourSquareAccountURI, "userId");
		photoUrl = model.createProperty(fourSquareAccountURI, "photoUrl");
		ownedByPerson = model.createProperty(fourSquareAccountURI,
				"ownedByPerson");
	}

	public void saveFourSquareAccount(String userIdStr, String photoUrlStr,
			String name) {
		String fourSquareAccount = URI + "/4sqAccount#" + userIdStr;
		String personURI = URI + "/person#" + name;
		Resource person = model.createResource(personURI);
		model.createResource(fourSquareAccount).addProperty(userId, userIdStr)
				.addProperty(this.photoUrl, photoUrlStr)
				.addProperty(ownedByPerson, person);
	}

	public Model getModel() {
		return model;
	}


}
