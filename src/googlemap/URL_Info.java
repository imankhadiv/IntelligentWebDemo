package googlemap;

import fi.foyt.foursquare.api.entities.CompactUser;
import fi.foyt.foursquare.api.entities.CompactVenue;

public class URL_Info {
	private User_4s user;
	private Venue_4s venue;
	
	
	public User_4s getUser() {
		return user;
	}

	public void setUser(User_4s user) {
		this.user = user;
	}

	public Venue_4s getVenue() {
		return venue;
	}

	public void setVenue(Venue_4s venue) {
		this.venue = venue;
	}

	
	
	public URL_Info(CompactUser _user,CompactVenue _venue)
	{
		if(_user!=null && _venue!=null)
		{
			user=new User_4s(_user);
			venue=new Venue_4s(_venue);
		}
		else
		{
			System.out.println("no input");
		}
	}

}
