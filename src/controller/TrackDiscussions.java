package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter.Tracking;
import twitter4j.GeoLocation;
import RdfModel.BaseModel;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;
import beans.Person;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Servlet implementation class TrackDiscussions
 */
public class TrackDiscussions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrackDiscussions() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action").equals("history")) {
		//codes for rdfa
			BaseModel baseModel;
			beans.Person person ;
			 String rdfFile = getServletContext().getRealPath("") + File.separator
					 + "WEB-INF" + File.separator + "RDF.rdf";

			String userName = request.getParameter("userName");
			String userId = request.getParameter("userId");
			baseModel = new BaseModel();
			if(userName != null && (!userName.equals(""))){
				
				person = baseModel.getRecordsByScreenName(userName, rdfFile);
				System.out.println(person);
			}else {
				person = baseModel.getRecordsByAccountId(userId, rdfFile);
				System.out.println(person);
				
			}
			person = baseModel.getallTweets(person.getUsrId(), rdfFile);
			request.setAttribute("person", person);
			request.getRequestDispatcher("/Tracking/history.jsp").forward(request, response);
		} else {
			doPost(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String keyword = request.getParameter("keyword");
		String hashtag = request.getParameter("hashtag");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String check = request.getParameter("checkbox");
		String radius = request.getParameter("radius");

		Tracking track;
		String key = "";
		if (keyword.length() > 0 && hashtag.length() > 0)
			key = keyword + " OR " + hashtag;
		else if (keyword.length() == 0)
			key = hashtag;
		else
			key = keyword;
		if ((latitude.equals("") || longitude.equals("")) && (check == null)) {

			track = new Tracking(key);
			track.setDefaultLocation(true);
		} else if (latitude.equals("") || longitude.equals("")) {
			track = new Tracking(key);
			track.setDefaultLocation(false);
		} else {
			track = new Tracking(key, new GeoLocation(Double.valueOf(latitude),
					Double.valueOf(longitude)));
		}
		if (!radius.equals("")) {
			track.setRadious(Integer.valueOf(radius));
		}
		List<Person> people = track.getUsers();
		List<Person> retwittPeople = track.getRetwittPeople();

		 String rdfFile = getServletContext().getRealPath("") + File.separator
		 + "WEB-INF" + File.separator + "RDF.rdf";
		RdfModel.Person person = new RdfModel.Person();
		Model model = person.getModelFromFile(rdfFile);
		// person.savePerson(people, liveInCity, twitterAccountId,
		// foursquareAccout)
		for (beans.Person p : people) {
			if (p.getLocation() == null)
				p.setLocation("N/A");
			person.savePerson(p.getName(), p.getLocation(), p.getTwitterId(),
					"N/A");
			model.add(person.getModel());
			TwitterAccount account = new TwitterAccount();
			if (p.getDescription() == null)
				p.setDescription("N/A");
			account.saveTwitterAccount(p.getName(),
					String.valueOf(p.getTwitterId()), p.getScreenName(),
					p.getDescription(), p.getProfilePicture());
			model.add(account.getModel());
			String rtpeople = "";
			for (beans.Person rp : retwittPeople) {
				if (rp.getTwittText().contains(p.getTwittText())) {
					rtpeople += rp.getScreenName() + ",";
				}
			}
			Tweet tweet = new Tweet();
			tweet.saveTweet2(String.valueOf(p.getTwitterId()),
					String.valueOf(p.getTweetId()),
					track.getTweetText(p.getTwittText()), p.getDate(),
					track.getShortURL(p.getTwittText()), rtpeople);
			model.add(tweet.getModel());
		}
		person.saveModel(rdfFile, model);

		HttpSession session = request.getSession();
		session.setAttribute("retwittPeople", retwittPeople);

		request.setAttribute("people", people);
		request.getRequestDispatcher("/Tracking/tracking-discussions.jsp")
				.forward(request, response);

	}
}
