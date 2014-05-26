package twitterUserSearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import twitter4j.FilterQuery;
import twitter4j.Twitter;
import twitter4j.TwitterStream;
import Models.TweetWithURL;

/**
 * Servlet implementation class SearchTweetsByUserServlet
 */
public class SearchTweetsByUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TwitterStreamingForUser tForUser;
	TwitterStream tws;
	 String workingDir;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchTweetsByUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		System.out.println(request);
		workingDir =getServletContext().getRealPath("/WEB-INF/RDF.rdf");
		int status = Integer.parseInt(request.getParameter("status"));
		System.out.println(status);
		String userScreenName="";
		int daysbefore = 0;
		if(status != 5 && status != 4)
		{
			userScreenName = request.getParameter("username");
			daysbefore = Integer.parseInt(request.getParameter("days"));
		}
		
		// 1 for normal search
		// 2 for start streaming
		// 3 for stream started and back to normal search
		// 4 for stop streaming only
		
		String json = "";
		if(status == 1)
		{
			//TODO regular search 
			json = normalSearch(userScreenName,daysbefore,daysbefore);
			
		}
		else if(status == 2)
		{
			//TODO start streaming for the first time
			tForUser = startStreaming(userScreenName);
		}
		else if(status == 3)
		{
			//TODO stop streaming
			stopStreaming();
			//TODO normal search
			json = normalSearch(userScreenName,daysbefore,daysbefore);
		}
		else if(status == 4)
		{
			//TODO stop streaming
			stopStreaming();
		}
		else if(status == 5)
		{
			//TODO query for the result of streaming
			if(tForUser!=null)
			{
				List<TweetWithURL> tweets = tForUser.getuRLList();
				if(tweets != null)
				{
					Gson gson = new Gson();
					json = gson.toJson(tweets);
				}
				else {
					json = "no result";
				}
			}
			else {
				json = "connection fails";
			}
		}
		PrintWriter out = response.getWriter();
		out.write(json);
	}
	/**
	 * 
	 * @param username
	 * @param daysbefore
	 * @param status
	 * @return
	 */
	public String normalSearch(String username, int daysbefore, int status)
	{
		
		System.out.println(username+daysbefore);
		String jsonString = "";
		GetTweetsByUser tt = new GetTweetsByUser();
		List<TweetWithURL> tweetsList = new ArrayList<TweetWithURL>();
		Twitter twitterConnection = null;
		try {
			twitterConnection = tt.init();
			tweetsList = tt.getSimpleTimeLine(twitterConnection,username,
					daysbefore,workingDir);
			Gson gson = new Gson();
			String json = gson.toJson(tweetsList);
			System.out.println(json);
			jsonString = json;
			System.out.println(jsonString);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
		return jsonString;
	}
	
	/**
	 * 
	 * @param _username
	 * @return
	 */
	public TwitterStreamingForUser startStreaming(String _username)
	{
		//get id of user by username
		long id = 0;
		String username = _username;
		//TODO get user long id
		GetTweetID tt_id = new GetTweetID();
		Twitter twitterConnection = null;
		try {
			twitterConnection = tt_id.init();
			id = tt_id.getUserID(twitterConnection, username);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
		TwitterStreamingForUser tt = new TwitterStreamingForUser();
		try {
			tws = tt.getTwitterStream(workingDir);
			int count = 0;
			//from user
			long[] idToFollow = new long[1];
			idToFollow[0] = id;
			//query
			String[] stringsToTrack = new String[0];
			double[][] locationsToTrack = new double[0][0];
			FilterQuery myFilterQuery = new FilterQuery(count, idToFollow, stringsToTrack,
					locationsToTrack);
			myFilterQuery.track(stringsToTrack);
			tws.filter(myFilterQuery);
		} catch (Exception e) {
			System.out.println("Cannot initialise Twitter");
			e.printStackTrace();
		}
		return tt;
	}
	
	public void stopStreaming()
	{
		//clean the tweet list of stream
		tForUser.setuRLList(null);
		tws.cleanUp();
		tws.shutdown();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
