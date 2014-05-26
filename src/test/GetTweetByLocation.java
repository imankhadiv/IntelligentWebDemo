package test;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import RdfModel.*;

import com.hp.hpl.jena.rdf.model.Model;

public class GetTweetByLocation {
	public String getSimpleTimeLine(Twitter twitter) {
		String resultString = "";
		try {
			// it creates a query and sets the geocode
			// requirement
			Query query = new Query("#sheffield");
			// Query query= new
			// Query("from:njy0612 geocode:53.381481,-1.4846917,1km");
			// //since:2014-03-27 geocode:53.3737444,-1.4782666,1km
			// geocode before from
			// query.setGeoCode(new GeoLocation(51.6908211,-0.4169319), 2,
			// Query.KILOMETERS);
			// System.out.println(query.toString());
			query.setCount(10);
			// it fires the query
			QueryResult result = twitter.search(query);

			// it cycles on the tweets
			List<Status> tweets = result.getTweets();

			// TODO save user to person and twitter account

			String workingDir = System.getProperty("user.dir");
			String fileName = workingDir + "/WebContent/WEB-INF/RDF.rdf";
			// ////Model model = ModelFactory.createDefaultModel();

			// TODO query if the file exist

			// String queryString = "PREFIX j.0:<http://intelligentweb/topic/> "
			// + "SELECT ?topic ?img ?name ?content"
			// + " WHERE{ " +
			// "     ?x j.0:topic ?topic ."
			// + "		?x j.0:img ?img ." +
			// "     ?x j.0:name ?name ." +
			// "     ?x j.0:content ?content }";
			//
			// com.hp.hpl.jena.query.Query jquery =
			// QueryFactory.create(queryString);
			// QueryExecution qe = QueryExecutionFactory.create(jquery, model);
			// ResultSet results = qe.execSelect();

			for (Status tweet : tweets) { // /gets the user
				User user = tweet.getUser();
				resultString += "@" + user.getScreenName() + " name"
						+ user.getName() + "\n";
				System.out.println(resultString);

					RdfModel.Person person = new RdfModel.Person();
					Model model = person.getModelFromFile(fileName);
					person.savePerson(filter(user.getName()).trim(), "", user.getId(), "");
					model.add(person.getModel());

					TwitterAccount twitterAccount = new TwitterAccount();
					twitterAccount.saveTwitterAccount(filter(user.getName()).trim(),
							String.valueOf(user.getId()),
							 filter(user.getScreenName()),filter(user.getDescription()),
							user.getProfileImageURL());
					
					model.add(twitterAccount.getModel());
					
					
			person.saveModel(fileName, model);//// you can see the result is the same but this way you do not need to hard code reading and writing to rdf file in each class
					
			}

		} catch (Exception te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets:" + te.getMessage());
			System.exit(-1);
		}
		return resultString;
	}

	private String filter(String str) {
		return (str.replace("#", "")).replace(" ", "");
	}

	private Twitter init() throws Exception {
		String consumerkey = "5aUFVk9PfsdKQusWkwiOOQ";
		String consumersecret = "17Ze1q8mYSRFPgFGV6sBJrybYUrjpMYR6JmP29xvNKE";
		String accesstoken = "2365765400-PzPlz6uUcHZsDdDwbozJpvfl4CxkC4mKSzyfuCQ";
		String accesstokensecret = "DYTINSWrKwjoelp3nmBRUlzuD0EDloauLX1HGyXOtYDvT";
		Twitter twitterConnection = initTwitter(consumerkey, consumersecret,
				accesstoken, accesstokensecret);
		return twitterConnection;
	}

	private Twitter initTwitter(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret)
				.setJSONStoreEnabled(true);
		return (new TwitterFactory(cb.build()).getInstance());
	}

	public static void main(String[] args) {
		GetTweetByLocation tt = new GetTweetByLocation();
		Twitter twitterConnection = null;
		try {
			twitterConnection = tt.init();
			System.out.print(tt.getSimpleTimeLine(twitterConnection));

		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();

		}

	}
}
//package test;
//
//import java.util.List;
//
//import twitter4j.Query;
//import twitter4j.QueryResult;
//import twitter4j.Status;
//import twitter4j.Twitter;
//import twitter4j.TwitterFactory;
//import twitter4j.User;
//import twitter4j.conf.ConfigurationBuilder;
//import RdfModel.*;
//
//import com.hp.hpl.jena.rdf.model.Model;
//
//public class GetTweetByLocation {
//	public String getSimpleTimeLine(Twitter twitter) {
//		String resultString = "";
//		try {
//			// it creates a query and sets the geocode
//			// requirement
//			Query query = new Query("#sheffield");
//			// Query query= new
//			// Query("from:njy0612 geocode:53.381481,-1.4846917,1km");
//			// //since:2014-03-27 geocode:53.3737444,-1.4782666,1km
//			// geocode before from
//			// query.setGeoCode(new GeoLocation(51.6908211,-0.4169319), 2,
//			// Query.KILOMETERS);
//			// System.out.println(query.toString());
//			query.setCount(10);
//			// it fires the query
//			QueryResult result = twitter.search(query);
//
//			// it cycles on the tweets
//			List<Status> tweets = result.getTweets();
//
//			// TODO save user to person and twitter account
//
//			String workingDir = System.getProperty("user.dir");
//			String fileName = workingDir + "/WebContent/WEB-INF/RDF.rdf";
//			// ////Model model = ModelFactory.createDefaultModel();
//
//			// TODO query if the file exist
//
//			// String queryString = "PREFIX j.0:<http://intelligentweb/topic/> "
//			// + "SELECT ?topic ?img ?name ?content"
//			// + " WHERE{ " +
//			// "     ?x j.0:topic ?topic ."
//			// + "		?x j.0:img ?img ." +
//			// "     ?x j.0:name ?name ." +
//			// "     ?x j.0:content ?content }";
//			//
//			// com.hp.hpl.jena.query.Query jquery =
//			// QueryFactory.create(queryString);
//			// QueryExecution qe = QueryExecutionFactory.create(jquery, model);
//			// ResultSet results = qe.execSelect();
//
//			for (Status tweet : tweets) { // /gets the user
//				User user = tweet.getUser();
//				resultString += "@" + user.getScreenName() + " name"
//						+ user.getName() + "\n";
//				System.out.println(resultString);
//				// /////////////////InputStream in =
//				// FileManager.get().open(fileName);
//				// model.read(in, null);
//
//<<<<<<< HEAD
//					
//
//				//////////////////if (in == null) {
//					/////////////////throw new IllegalArgumentException("File: " + fileName
//							/////////////+ " not found");
//				////////////} else {
//					//////////model.read(in, null);
//
//
//					RdfModel.Person person = new RdfModel.Person();
//					Model model = person.getModelFromFile(fileName);
//					person.savePerson(filter(user.getName()).trim(), "", user.getId(), "");
//					model.add(person.getModel());
//
//					TwitterAccount twitterAccount = new TwitterAccount();
//					twitterAccount.saveTwitterAccount(filter(user.getName()).trim(),
//							String.valueOf(user.getId()),
//							 filter(user.getScreenName()),filter(user.getDescription()),
//							user.getProfileImageURL());
//					
//					model.add(twitterAccount.getModel());
//					
////					FileWriter out = null;
////					try {
////						out = new FileWriter(fileName);
////						model.write(out);
////						out.close();
////					} catch (IOException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					} 
////					finally {
////						if (out != null)
////							try {
////								out.close();
////							} catch (IOException e) {
////								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
////						if (in != null)
////							try {
////								in.close();
////							} catch (IOException e) {
////								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
////					}
////=======
//					
//			person.saveModel(fileName, model);//// you can see the result is the same but this way you do not need to hard code reading and writing to rdf file in each class
//					
//
//				}
//			//}
//=======
//				// ////////////////if (in == null) {
//				// ///////////////throw new IllegalArgumentException("File: " +
//				// fileName
//				// ///////////+ " not found");
//				// //////////} else {
//				// ////////model.read(in, null);
//
//				Person person = new Person();
//				Model model = person.getModelFromFile(fileName);
//				person.savePerson(filter(user.getName()).trim(), "",
//						user.getLocation(), "", user.getId(), "");
//				model.add(person.getModel());
//
//				TwitterAccount twitterAccount = new TwitterAccount();
//				twitterAccount.saveTwitterAccount(
//						filter(user.getName()).trim(),
//						String.valueOf(user.getId()),
//						filter(user.getScreenName()),
//						filter(user.getDescription()),
//						user.getProfileImageURL());
//
//				model.add(twitterAccount.getModel());
//
//				// FileWriter out = null;
//				// try {
//				// out = new FileWriter(fileName);
//				// model.write(out);
//				// out.close();
//				// } catch (IOException e) {
//				// // TODO Auto-generated catch block
//				// e.printStackTrace();
//				// }
//				// finally {
//				// if (out != null)
//				// try {
//				// out.close();
//				// } catch (IOException e) {
//				// // TODO Auto-generated catch block
//				// e.printStackTrace();
//				// }
//				// if (in != null)
//				// try {
//				// in.close();
//				// } catch (IOException e) {
//				// // TODO Auto-generated catch block
//				// e.printStackTrace();
//				// }
//				// }
//				// =======
//
//				person.saveModel(fileName, model);// // you can see the result
//													// is the same but this way
//													// you do not need to hard
//													// code reading and writing
//													// to rdf file in each class
//
//			}
//			// }
//>>>>>>> FETCH_HEAD
//		} catch (Exception te) {
//			te.printStackTrace();
//			System.out.println("Failed to search tweets:" + te.getMessage());
//			System.exit(-1);
//		}
//		return resultString;
//	}
//
//	private String filter(String str) {
//		return (str.replace("#", "")).replace(" ", "");
//	}
//
//	private Twitter init() throws Exception {
//		String consumerkey = "5aUFVk9PfsdKQusWkwiOOQ";
//		String consumersecret = "17Ze1q8mYSRFPgFGV6sBJrybYUrjpMYR6JmP29xvNKE";
//		String accesstoken = "2365765400-PzPlz6uUcHZsDdDwbozJpvfl4CxkC4mKSzyfuCQ";
//		String accesstokensecret = "DYTINSWrKwjoelp3nmBRUlzuD0EDloauLX1HGyXOtYDvT";
//		Twitter twitterConnection = initTwitter(consumerkey, consumersecret,
//				accesstoken, accesstokensecret);
//		return twitterConnection;
//	}
//
//	private Twitter initTwitter(String consumerKey, String consumerSecret,
//			String accessToken, String accessTokenSecret) throws Exception {
//		ConfigurationBuilder cb = new ConfigurationBuilder();
//		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
//				.setOAuthConsumerSecret(consumerSecret)
//				.setOAuthAccessToken(accessToken)
//				.setOAuthAccessTokenSecret(accessTokenSecret)
//				.setJSONStoreEnabled(true);
//		return (new TwitterFactory(cb.build()).getInstance());
//	}
//
//	public static void main(String[] args) {
//		GetTweetByLocation tt = new GetTweetByLocation();
//		Twitter twitterConnection = null;
//		try {
//			twitterConnection = tt.init();
//			System.out.print(tt.getSimpleTimeLine(twitterConnection));
//
//		} catch (Exception e) {
//			System.out.println("Cannot initialise Twitter");
//			e.printStackTrace();
//
//		}
//
//	}
//}
