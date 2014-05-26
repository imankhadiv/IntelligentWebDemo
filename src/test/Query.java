package test;

import java.io.IOException;

import RdfModel.BaseModel;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class Query {
	
	public static void main(String[] args) throws IOException
	{
		String userId = "31714483";
		//String screenname = "waynechadburn";
		String workingDir = System.getProperty("user.dir");
		String fileName = workingDir + "/WebContent/WEB-INF/RDF.rdf";
		//InputStream in = FileManager.get().open(fileName);
//		Model model = ModelFactory.createDefaultModel();
//		model.read(in,null);
//		in.close();
////		
//		String queryString = "PREFIX twitter: <http://somewhere/twitter#> "+
//				"PREFIX person: <http://somewhere/person#> "+
//				"SELECT ?name ?userId ?sceenName ?photoUrl  "+
//				"WHERE { ?twitter twitter:userId \""+userId+"\" ."+
//				"?twitter twitter:sceenName \""+screenname+"\" . "+
//				"?twitter twitter:sceenName ?sceenName . "+
//				"?twitter twitter:userId ?userId . " +
//				"?twitter twitter:photoUrl ?photoUrl . " +
//				"?twitter twitter:ownedByPerson ?person . " +
//				"?person person:name ?name . " +
//				"}";
		
		
		
//		
//		 com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);
//	    	
//    	 // Execute the query and obtain results
//    	 QueryExecution qe = QueryExecutionFactory.create(query, model);
    	// ResultSet results = qe.execSelect();
		BaseModel base = new BaseModel();
    	 ResultSet results = base.getRecordsByScreenName("waynechadburn", fileName);
    	// ResultSet results = base.getRecordsByAccountId("31714483", fileName);
		
		try {
			// simple select
			if(results.hasNext()){
				QuerySolution qs = results.next();
				System.out.println(qs.getLiteral("?sceenName"));
				System.out.println(qs.getLiteral("?userId"));
				System.out.println(qs.getLiteral("?photoUrl"));
				System.out.println(qs.getLiteral("?name"));
			}
		} finally {
			//qe.close();
		}
	
	}
}
