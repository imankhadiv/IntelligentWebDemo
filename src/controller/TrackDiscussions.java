package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter.Tracking;
import twitter4j.GeoLocation;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;
import beans.Person;

import com.hp.hpl.jena.rdf.model.Model;

import database.Data;

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
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team019?user=team019&password=077cea79";
				Connection con = DriverManager.getConnection(DB);
				Data data = new Data(con);
				request.setAttribute("people", data.getUsers());
				request.getRequestDispatcher("/Tracking/history.jsp").forward(
						request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (con != null)
					try {
						con.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
			}
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

		// String rdfFile = getServletContext().getRealPath("") + File.separator
		// + "WEB-INF" + File.separator + "RDF.rdf";
		String rdfFile = "/Users/Iman/Documents/workspace/web2014/IntelligentWebDemo/WebContent/WEB-INF/RDF.rdf";
		// this file path is temporary because we run the program in eclipse
		// we can not see the real file if we use getRealPath
		// for deploying the above rdfFile should be uncommented.
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
			tweet.saveTweet(String.valueOf(p.getTwitterId()),
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
