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
	
	public void saveTwitterAccount(String name, String userId, String ScreenName, String description, String userPhotoUrl){
		String twitterAccountURL =  URI+"/twitterAccount#" + userId;
		// get source for person
		String personURI = URI+"/person#" + name;
		Resource person = model.createResource(personURI);
<<<<<<< HEAD
		if(model.getResource(personURI)!=null)
		{
			System.out.println("exist");
			model.getResource(personURI).addProperty(propUserId, userId)
			.addProperty(propScreenName, ScreenName)
			.addProperty(propDescription, description).addProperty(propUserPhotoUrl, userPhotoUrl).addProperty(propOwnedByPerson, person);

		}
		else{
			System.out.println("not exist");
			model.createResource(twitterAccountURL)
			.addProperty(propUserId, userId)
			.addProperty(propScreenName, ScreenName)
			.addProperty(propDescription, description).addProperty(propUserPhotoUrl, userPhotoUrl).addProperty(propOwnedByPerson, person);
		}
		
=======
		model.createResource(twitterAccountURL)
				.addProperty(propUserId, userId)
				.addProperty(propScreenName, ScreenName)
				.addProperty(propDescription, description)
				.addProperty(propUserPhotoUrl, userPhotoUrl)
				.addProperty(propOwnedByPerson, person);
>>>>>>> FETCH_HEAD
	}
	
	public Model getModel(){
		return model;
	}
	
	public static void main(String[] args)
	{
		String workingDir = System.getProperty("user.dir");
		System.out.println(workingDir);
		String fileName = workingDir + "/WebContent/WEB-INF/test.rdf";
		//InputStream in = FileManager.get().open(fileName);
//		Model modelMain = ModelFactory.createDefaultModel();
//		// comment this line for the first time
//		modelMain.read(in, null);
//		modelMain.write(System.out);
		TwitterAccount twitterAccount = new TwitterAccount();
		
		Model modelMain = twitterAccount.getModelFromFile(fileName);
		System.out.println("..............///////");
		modelMain.write(System.out);
		
		twitterAccount.saveTwitterAccount("Jianyue Ni", "111", "njjy0612", "", "xxxx");
		modelMain.add(twitterAccount.getModel());
		Person personrdf = new Person();
//		personrdf.savePerson("Jianyue NI2", "1988-06-21", "sheffield", "male", 111,"");
//		personrdf.savePerson("Jianyue NI3", "1988-06-21", "sheffield", "male", 111,"");
//		personrdf.savePerson("Jianyue NI4", "1988-06-21", "sheffield", "male", 111,"");
//		personrdf.savePerson("Amir", "111", "sheffield", "male", 111,"");
		modelMain.add(personrdf.getModel());
		
		twitterAccount.saveModel(fileName, modelMain);
		
		
	}
}
