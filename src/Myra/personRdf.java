package Myra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.VCARD;

public class personRdf {
	
//	public static void main(String args[])
//	{
//		savePerson("Amirah","Ibrahim","19.03.1990","KL","female","oOSyaza","MyraSyazana");
//	
//		System.out.println("hehehe");
//	}
	 Model model;
	 Property propFirstName;
	 Property propSecondName;
	 Property propBirthday;
	 Property propLiveInCity;
	 Property propGender;
	 Property propTwitterAccount;
	 Property propFoursquareAccout;
	
	public personRdf()
	{
		String personURI = "http://somewhere/person";
		model = ModelFactory.createDefaultModel();
		//Model model = ModelFactory.createDefaultModel();
		
		//create schema
		propFirstName = model.createProperty(personURI, "firstName");
		propSecondName = model.createProperty(personURI, "secondName");
		propBirthday = model.createProperty(personURI, "birthday");
		propLiveInCity = model.createProperty(personURI, "liveInCity");
		propGender= model.createProperty(personURI, "gender");
		propTwitterAccount= model.createProperty(personURI, "twitterAccount");
		propFoursquareAccout= model.createProperty(personURI, "foursquareAccout");
	}
	
	public void savePerson(String firstName,String secondName,String birthday,String liveInCity,String gender,String twitterAccount,String foursquareAccout)
	{
//		String personURI = "http://somewhere/person";
//				
//		//Model model = ModelFactory.createDefaultModel();
//		
//		//create schema
//		Property propFirstName = model.createProperty(personURI, "firstName");
//		Property propSecondName = model.createProperty(personURI, "secondName");
//		Property propBirthday = model.createProperty(personURI, "birthday");
//		Property propLiveInCity = model.createProperty(personURI, "liveInCity");
//		Property propGender= model.createProperty(personURI, "gender");
//		Property propTwitterAccount= model.createProperty(personURI, "twitterAccount");
//		Property propFoursquareAccout= model.createProperty(personURI, "foursquareAccout");
//		
		//create instance
		Resource newPerson = model.createResource().addProperty(propFirstName, firstName).addProperty(propSecondName, secondName)
		.addProperty(propBirthday, birthday).addProperty(propLiveInCity, liveInCity).addProperty(propGender, gender)
		.addProperty(propTwitterAccount, twitterAccount).addProperty(propFoursquareAccout, foursquareAccout);
		
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
}
