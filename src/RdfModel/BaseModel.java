package RdfModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class BaseModel {

	/**
	 * This method is implemented for getting the model from rdf file inside
	 * servlet
	 * 
	 * @param fileName
	 * @return
	 */
	public Model getModelFromFile(String fileName) {
		InputStream in = FileManager.get().open(fileName);
		if (in == null) {
			System.out.println("inside file");
			throw new IllegalArgumentException("File: " + fileName
					+ "Not found");
		} else {
			Model model = ModelFactory.createDefaultModel();
			model.read(in, null);
			//model.write(System.out);
			return model;
		}
	}

	/**
	 * This method is implemented for saving the model in rdf file inside
	 * servlet
	 * 
	 * @param fileName
	 * @param model
	 */
	public void saveModel(String fileName, Model model) {

		FileWriter out = null;
		try {
			out = new FileWriter(fileName);
			model.write(out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
	/**
	 * 
	 * @param sreenName
	 * @param fileName
	 * @return
	 */
	public ResultSet getRecordsByScreenName(String screenName, String fileName) {

		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "
				+ "PREFIX person: <http://somewhere/person#> "
				+ "SELECT ?name ?userId ?sceenName ?photoUrl  "
				+ "WHERE { ?twitterAccount twitterAccount:sceenName \""
				+ screenName
				+ "\" ."
				+ "?twitterAccount twitterAccount:sceenName ?sceenName . "
				+ "?twitterAccount twitterAccount:userId ?userId . "
				+ "?twitterAccount twitterAccount:photoUrl ?photoUrl . "
				+ "?twitterAccount twitterAccount:ownedByPerson ?person . "
				+ "?person person:name ?name . " + "}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe.execSelect();

	}
	/**
	 * 
	 * @param userId
	 * @param fileName
	 * @return
	 */

	public ResultSet getRecordsByAccountId(String userId,String fileName) {
		Model model = getModelFromFile(fileName);

		String queryString = "PREFIX twitterAccount: <http://somewhere/twitterAccount#> "+
				"PREFIX person: <http://somewhere/person#> "+
				"SELECT ?name ?userId ?sceenName ?photoUrl  "+
				"WHERE { ?twitterAccount twitterAccount:userId \""+userId+"\" ."+
				"?twitterAccount twitterAccount:sceenName ?sceenName . "+
				"?twitterAccount twitterAccount:userId ?userId . " +
				"?twitterAccount twitterAccount:photoUrl ?photoUrl . " +
				"?twitterAccount twitterAccount:ownedByPerson ?person . " +
				"?person person:name ?name . " +
				"}";

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe.execSelect();

	}

	public ResultSet getTweetsByAccountId(String sreenName) {
		return null;

	}

}
