package test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

public class WriteRDF {

	// some definitions
	static String tutorialURI = "http://hostname/rdf/tutorial/";
	static String briansName = "Brian McBride";
	static String briansEmail1 = "brian_mcbride@hp.com";
	static String briansEmail2 = "brian_mcbride@hpl.hp.com";
	static String title = "An Introduction to RDF and the Jena API";
	static String date = "23/01/2001";

	public static void main(String args[]) {

		// some definitions
		String personURI = "http://somewhere/JohnSmith";
		String givenName = "John";
		String familyName = "Smith";
		String fullName = givenName + " " + familyName;
		// create an empty model
		Model model = ModelFactory.createDefaultModel();

		// create the resource
		// and add the properties cascading style
		Resource johnSmith = model
				.createResource(personURI)
				.addProperty(VCARD.FN, fullName)
				.addProperty(
						VCARD.N,
						model.createResource()
								.addProperty(VCARD.Given, givenName)
								.addProperty(VCARD.Family, familyName));

		// now write the model in XML form to a file
		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/test.rdf";
		System.out.println(fileName);
		OutputStream out = null;
		try {
			out = new FileOutputStream(fileName, true);
			model.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			model.write(System.out);
		}
	}
}
