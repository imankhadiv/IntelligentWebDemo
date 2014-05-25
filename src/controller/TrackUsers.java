package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter.FrequentKeywords;
import twitter.MixQuery;
import twitter4j.GeoLocation;
import twitter4j.ResponseList;
import twitter4j.Status;
import RdfModel.Tweet;
import RdfModel.TwitterAccount;
import beans.Person;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Servlet implementation class TrackUsers
 */
public class TrackUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrackUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		FrequentKeywords fr = new FrequentKeywords(userId);
		fr.getUsers();
		// String rdfFile = getServletContext().getRealPath("") + File.separator
		// + "WEB-INF" + File.separator + "RDF.rdf";
		String rdfFile = "/Users/Iman/Documents/workspace/web2014/IntelligentWebDemo/WebContent/WEB-INF/RDF.rdf";
		// this file path is temporary because we run the program in eclipse
		// we can not see the real file if we use getRealPath
		// for deploying the above rdfFile should be uncommented.
		System.out.println(rdfFile);
		ResponseList<Status> statuses = fr.getUserTimeline();

		RdfModel.Person person = new RdfModel.Person();
		Model model = person.getModelFromFile(rdfFile);
		person.savePerson(statuses.get(0).getUser().getName(), statuses.get(0)
				.getUser().getLocation(), statuses.get(0).getUser().getId(), "");
		model.add(person.getModel());
		for (Status item : statuses) {

			TwitterAccount account = new TwitterAccount();
			account.saveTwitterAccount(item.getUser().getName(), String
					.valueOf(item.getUser().getId()), item.getUser()
					.getScreenName(), item.getUser().getDescription(), item
					.getUser().getOriginalProfileImageURL());
			model.add(account.getModel());
			Tweet tweet = new Tweet();
			tweet.saveTweet(String.valueOf(item.getUser().getId()),
					String.valueOf(item.getId()),
					fr.getTweetText(item.getText()),
					String.valueOf(item.getCreatedAt()),
					fr.getShortURL(item.getText()));

			model.add(tweet.getModel());

		}
		person.saveModel(rdfFile, model);

		request.setAttribute("status", statuses);

		request.getRequestDispatcher("/Tracking/profile.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String mix = request.getParameter("mixed");

		if (mix != null && mix.equals("mixed")) {

			String longitude = request.getParameter("longitude");
			String latitude = request.getParameter("latitude");
			String daysAgo = request.getParameter("daysAgo");
			String radius = request.getParameter("radius");
			String check = request.getParameter("checkbox");
			String number = request.getParameter("number");
			MixQuery mixQ;

			if ((latitude.equals("") || longitude.equals(""))
					&& (check == null)) {

				mixQ = new MixQuery(Integer.valueOf(daysAgo));
				mixQ.setDefaultLocation(true);

			} else if (latitude.equals("") || longitude.equals("")) {
				mixQ = new MixQuery(Integer.valueOf(daysAgo));
				mixQ.setDefaultLocation(false);
			} else {
				mixQ = new MixQuery(new GeoLocation(Double.valueOf(latitude),
						Double.valueOf(longitude)), Integer.valueOf(daysAgo));
			}
			if (!radius.equals("")) {
				mixQ.setRadius(Integer.valueOf(radius));
			}
			mixQ.go();
			request.setAttribute("number", Integer.valueOf(number));
			request.setAttribute("words", mixQ.getWords());
			request.getRequestDispatcher("/Tracking/mixqueries.jsp").forward(
					request, response);

		} else {

			String userIds = request.getParameter("userIds");
			String[] ids = userIds.split(",");
			Integer number = Integer.valueOf(request.getParameter("number"));

			String daysAGo = request.getParameter("daysAgo");
			FrequentKeywords word = new FrequentKeywords(ids, number,
					Integer.valueOf(daysAGo));
			List<Person> people = word.getUsers();
			Map<String, Integer> map = word.getMap();
			System.out.println(people);

			request.setAttribute("number", number);
			request.setAttribute("people", people);
			request.setAttribute("map", map);
			request.getRequestDispatcher("/Tracking/query-users.jsp").forward(
					request, response);
			System.out.println(map);
			for (Person item : people) {
				System.out.println("...." + item.getMap());
			}

		}

	}

}
