package Myra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class mainModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model modelMain = ModelFactory.createDefaultModel();
		personRdf personrdf = new personRdf();
		foursquareAccountRdf fsrdf = new foursquareAccountRdf();
		twitterAccountRdf twrdf = new twitterAccountRdf();
		tweetRdf trdf = new tweetRdf();
		venueRdf venrdf = new venueRdf();
		
		personrdf.savePerson("Amirah1213", "Ibrahim", "19.03.1990", "KL", "female", "ooSyaza", "Myrasyazana");
		personrdf.savePerson("Amirah2", "Ibrahim", "19.03.1990", "KL", "female", "ooSyaza", "Myrasyazana");
		fsrdf.saveFoursquareAccount("MyraSyazana", "http://amirah/img", "hello", "MyraSyazana");
		twrdf.saveTwitterAccount("MyraSyazana", "oOSyaza", "My page", " ", " ");
		//trdf.saveTweetRdf("tweetId", "test", "shortUrl", "22 May 2014", "testing ", "oOsyaza");
		//venrdf.saveVenue("Sheffield", "-1.470085000000040000", "53.381128999999990000");
		
		modelMain.add(personrdf.getModel());
		modelMain.add(fsrdf.getModel());
		modelMain.add(twrdf.getModel());
		//modelMain.add(trdf.getModel());
		//modelMain.add(venrdf.getModel());
		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/test.rdf";
		File file = new File(fileName);
		try {
			FileWriter writer = new FileWriter(file);
			modelMain.write(writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
