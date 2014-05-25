package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import RdfModel.BaseModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class TwitterAccount extends BaseModel {

	Model model;
	Property propUserId;
	Property propScreenName;
	Property propDescription;
	Property propUserPhotoUrl;
	Property propOwnedByPerson;
	
	
	public TwitterAccount(){
		
		String twitterAccountURI ="http://somewhere/twitter#";
		model = ModelFactory.createDefaultModel();
		
		//create schema
		propUserId = model.createProperty(twitterAccountURI, "userId");
		propScreenName = model.createProperty(twitterAccountURI, "sceenName");
		propDescription = model.createProperty(twitterAccountURI, "description");
		propUserPhotoUrl = model.createProperty(twitterAccountURI, "photoUrl");
		propOwnedByPerson = model.createProperty(twitterAccountURI, "ownedByPerson");

	}
	
	public void saveTwitterAccount(String name, String userId, String ScreenName, String description, String userPhotoUrl){
		String twitterAccountURL =  "http://somewhere/twitter_account#" + userId;
		String personURI = "http://somewhere/person#";
		Resource person = model.createResource(personURI + name);
		Resource newTwitterAccount = model.createResource(twitterAccountURL)
				.addProperty(propUserId, userId)
				.addProperty(propScreenName, ScreenName)
				.addProperty(propDescription, description).addProperty(propUserPhotoUrl, userPhotoUrl).addProperty(propOwnedByPerson, person);
		
	}
	
	public Model getModel(){
		return model;
	}
	
	public static void main(String[] args)
	{
		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/test.rdf";
		InputStream in = FileManager.get().open(fileName);
		Model modelMain = ModelFactory.createDefaultModel();
		// comment this line for the first time
		modelMain.read(in, null);
		modelMain.write(System.out);
		
		TwitterAccount twitterAccount = new TwitterAccount();
		twitterAccount.saveTwitterAccount("Jianyue Ni", "111", "njjy0612", "", "xxxx");
		modelMain.add(twitterAccount.getModel());
		Person personrdf = new Person();
		personrdf.savePerson("Jianyue NI", "1988-06-21", "sheffield", "male", 111,"");
		personrdf.savePerson("Jianyue NI", "1988-06-21", "sheffield", "male", 111,"");
		personrdf.savePerson("Jianyue NI", "1988-06-21", "sheffield", "male", 111,"");
		modelMain.add(personrdf.getModel());
		
		FileWriter out = null;
		try {
			out = new FileWriter(fileName);
			modelMain.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}
}
