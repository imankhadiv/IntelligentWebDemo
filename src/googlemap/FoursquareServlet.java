package googlemap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.google.gson.Gson;

import fi.foyt.foursquare.api.FoursquareApiException;


/**
 * Servlet implementation class FoursquareServlet
 */
public class FoursquareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoursquareServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			FoursquareApiException, JSONException{
    	String shortURL = request.getParameter("shorturl");
    	FourSquare fs = new FourSquare();
    	URL_Info currentInfo = fs.getLocationInformation(shortURL);
    	Gson gson = new Gson();
    	String json = gson.toJson(currentInfo);
    	PrintWriter out = response.getWriter();
    	out.write(json);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			try {
				processRequest(request,response);
			} catch (FoursquareApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}

