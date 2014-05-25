package test;

import RdfModel.BaseModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Person extends BaseModel {
	 Model model;
	 Property Name;
	 Property Birthday;
	 Property LiveInCity;
	 Property Gender;
	 Property hasTwitterAccount;
//	 Resource propFoursquareAccout;
	
	public Person()
	{
		String personURI = "http://somewhere/person#";
		model = ModelFactory.createDefaultModel();
		//create schema
		Name = model.createProperty(personURI, "name");
		Birthday = model.createProperty(personURI, "birthday");
		LiveInCity = model.createProperty(personURI, "liveInCity");
		Gender= model.createProperty(personURI, "gender");
		hasTwitterAccount= model.createProperty(personURI, "has_twitterAccount");
//		propFoursquareAccout= model.createProperty(personURI, "foursquareAccout");
	}

	public void savePerson(String name,
			String birthday, String liveInCity, String gender,
			long twitterAccount_id, String foursquareAccout) {
		// create instance
		String twitterAccountURI = "http://somewhere/twitterAccount#";
		Resource twitterAccount = model.createResource(twitterAccountURI+twitterAccount_id);
		String personalURI =  "http://somewhere/person#"+name;
		Resource newPerson = model.createResource(personalURI)
				.addProperty(Name, name)
				.addProperty(Birthday, birthday)
				.addProperty(LiveInCity, liveInCity)
				.addProperty(Gender, gender)
				.addProperty(hasTwitterAccount, twitterAccount);//addProperty(propFoursquareAccout, foursquareAccout);
		
		/*File file = new File("C:/Users/Mira/workspace/IntelligentWeb/src/ontology/personFile.rdf");
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
	
	public static void main(String[] args)
	{
		Model modelMain = ModelFactory.createDefaultModel();
		Person personrdf = new Person();
		personrdf.savePerson("Ni Jianyue", "1988-06-21", "sheffield", "male", 111,"");
		modelMain.add(personrdf.getModel());
		modelMain.write(System.out);
	}
}
