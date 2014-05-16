package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter.MixQuery;
import twitter.Tracking;
import twitter4j.GeoLocation;

/**
 * Servlet implementation class MixedQueries
 */
public class MixedQueries extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MixedQueries() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String daysAgo = request.getParameter("daysAgo");
		String radius = request.getParameter("radius");
		String check = request.getParameter("checkbox");
		String number = request.getParameter("number");
		MixQuery mix;
		
		if ((latitude.equals("") || longitude.equals("")) && (check == null)) {

			mix = new MixQuery(Integer.valueOf(daysAgo));
			mix.setDefaultLocation(true);
			
		} else if (latitude.equals("") || longitude.equals("")) {
			mix = new MixQuery(Integer.valueOf(daysAgo));
			mix.setDefaultLocation(false);
		} else {
			mix = new MixQuery(new GeoLocation(Double.valueOf(latitude),
					Double.valueOf(longitude)),Integer.valueOf(daysAgo));
		}
		if(!radius.equals("")){
			mix.setRadius(Integer.valueOf(radius));
		}
		mix.go();
		request.setAttribute("number", Integer.valueOf(number));
		request.setAttribute("words", mix.getWords());
		request.getRequestDispatcher("/Tracking/mixqueries.jsp").forward(request, response);
		
	}
	

}
