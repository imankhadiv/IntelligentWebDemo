package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter.FrequentKeywords;
import beans.Person;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		 FrequentKeywords fr = new FrequentKeywords(userId);
		 fr.getUsers();
		 
		request.setAttribute("status", fr.getUserTimeline());
		System.out.println(fr.getUserTimeline());
		
//		if(fr.getUserTimeline().size() == 0 || fr.getUserTimeline() == null){
//			request.setAttribute("error", "Nobody Found");
//		}
		request.getRequestDispatcher("/Tracking/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String userIds = request.getParameter("userIds");
		String[] ids = userIds.split(",");
		Integer number = Integer.valueOf(request.getParameter("number"));
		
		String daysAGo = request.getParameter("daysAgo");
		FrequentKeywords word = new FrequentKeywords(ids,number,Integer.valueOf(daysAGo));
		List<Person> people = word.getUsers();
		Map<String, Integer> map = word.getMap();
		System.out.println(people);
		
		request.setAttribute("number", number);
		request.setAttribute("people", people);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/Tracking/query-users.jsp").forward(request, response);
		System.out.println(map);
		for(Person item:people) {
			System.out.println("...."+item.getMap());
		}
		
	
	
	}

}
