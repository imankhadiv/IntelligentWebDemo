package twitterVenueSearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Models.User;
import sun.net.www.content.audio.x_aiff;
import twitter4j.FilterQuery;
import twitter4j.Twitter;
import twitter4j.TwitterStream;

/**
 * Servlet implementation class SearchUserWithVenueServlet
 */
public class SearchUserWithVenueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TwitterStream tws ;
	TwitterStreaming ts;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchUserWithVenueServlet() {
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
		// all validation is in jquery
		
		String workingDir =getServletContext().getRealPath("/WEB-INF/RDF.rdf");
		System.out.println("work dir"+workingDir);
		double latitude = Double.parseDouble(request.getParameter("lat"));
		double longitude = Double.parseDouble(request.getParameter("lng"));
		int rangeString = Integer.parseInt(request.getParameter("r"));
		// get x day and format the since string
		int daysbefore = Integer.parseInt(request.getParameter("days"));
		boolean start = Boolean.parseBoolean(request.getParameter("start"));
		// get venue name
		String venue_name = request.getParameter("venue_name");
		System.out.println("venue name is :"+venue_name);

		String country = request.getParameter("country");
		System.out.println("country name is :"+country);
		
		String city = request.getParameter("address");
		System.out.println("city name is :"+city);
		
		String address = request.getParameter("address");
		System.out.println("address name is :"+address);
		
		String postcode = request.getParameter("postcode");
		System.out.println("postcode name is ;"+postcode);
		
		String photoURL = request.getParameter("photoURL");
		System.out.println("photoURL name is ;"+photoURL);
		
		System.out.println("start"+start);
		String streamstopString = "asdf";
		streamstopString = request.getParameter("stream");
		System.out.println("blala"+streamstopString);

		if (streamstopString == null) {
			if (start == false && daysbefore == 0) {
				// TODO streaming
				System.out.println("start streaming");
				try {
					 ts = new TwitterStreaming();
					tws = ts.getTwitterStream(workingDir);
					int count = 0;
					long[] idToFollow = new long[0];
					// query
					String[] stringsToTrack = new String[0];
					// date
					double[][] locationsToTrack = {
							{ (longitude - 0.1 * rangeString),
									(latitude - 0.1 * rangeString) },
							{ (longitude + 0.1 * rangeString),
									(latitude + 0.1 * rangeString) } };
					FilterQuery myFilterQuery = new FilterQuery(count,
							idToFollow, stringsToTrack, locationsToTrack);
					myFilterQuery.track(stringsToTrack);
					System.out.println(myFilterQuery.toString());
					tws.filter(myFilterQuery);

				} catch (Exception e) {
					System.out.println("Cannot initialise Twitter");
					e.printStackTrace();
				}
			} else {
				// TODO search
				Calendar calendar = Calendar.getInstance();
				calendar.add(calendar.DATE, (-1) * daysbefore);
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
				String sinceString = formate.format(calendar.getTime());
				System.out.println(latitude + "+" + longitude + "+"
						+ rangeString + "+" + daysbefore + "+" + sinceString);
				GetTweetByLocation tt = new GetTweetByLocation();
				Twitter twitterConnection = null;
				try {
					twitterConnection = tt.init();
					List<User> myuserList = new ArrayList<User>();
					myuserList = tt.getSimpleTimeLine(twitterConnection,
							latitude, longitude, rangeString, sinceString,workingDir);
					Gson gson = new Gson();
					String json = gson.toJson(myuserList);
					System.out.println(json);
					PrintWriter out = response.getWriter();
					out.write(json);
				} catch (Exception e) {
					System.out.println("Cannot initialise Twitter");
					e.printStackTrace();
				}
			}
		} else if (streamstopString.equals("stop")) {
			System.out.println("123123");
				tws.cleanUp();
				tws.shutdown();
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String jsonString = gson.toJson(ts.getUserList());
		System.out.println(jsonString);
		PrintWriter out = response.getWriter();
    	out.write(jsonString);
	}

}
