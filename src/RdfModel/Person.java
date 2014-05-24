package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Person {
	private final String URI = "http://somewhere";
	private Model model;
	private Property Name;
	private Property Birthday;
	private Property LiveInCity;
	private Property Gender;
	private Property hasTwitterAccount;

	// Resource propFoursquareAccout;

	public Person() {
		String personURI = URI+"/person#";
		model = ModelFactory.createDefaultModel();
		// create schema
		Name = model.createProperty(personURI, "name");
		Birthday = model.createProperty(personURI, "birthday");
		LiveInCity = model.createProperty(personURI, "liveInCity");
		Gender = model.createProperty(personURI, "gender");
		hasTwitterAccount = model.createProperty(personURI,
				"has_twitterAccount");
		// propFoursquareAccout= model.createProperty(personURI,
		// "foursquareAccout");
	}

	public void savePerson(String name, String birthday, String liveInCity,
			String gender, long twitterAccount_id, String foursquareAccout) {
		// create instance
		String twitterAccountURI =URI+"/twitterAccount#";
		Resource twitterAccount = model.createResource(twitterAccountURI
				+ twitterAccount_id);
		String personalURI = URI+"/person#" + name;
		model.createResource(personalURI).addProperty(Name, name)
				.addProperty(Birthday, birthday)
				.addProperty(LiveInCity, liveInCity)
				.addProperty(Gender, gender)
				.addProperty(hasTwitterAccount, twitterAccount);// addProperty(propFoursquareAccout,
																// foursquareAccout);
	}

	public Model getModel() {
		return model;
	}

	public static void main(String[] args) {
		Model modelMain = ModelFactory.createDefaultModel();
		Person personrdf = new Person();
		personrdf.savePerson("Ni Jianyue", "1988-06-21", "sheffield", "male",
				111, "");
		modelMain.add(personrdf.getModel());
		modelMain.write(System.out);
	}
}
