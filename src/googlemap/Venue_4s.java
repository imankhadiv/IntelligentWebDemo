package googlemap;

import fi.foyt.foursquare.api.entities.Category;
import fi.foyt.foursquare.api.entities.CompactVenue;

public class Venue_4s {
	private String venue_name;
	private String address;
	private String postcode;
	private String country;
	private String city;
	private Double latitude;
	private Double longitude;
	private String URL;
	private String photoURL;
	
	

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public Venue_4s(CompactVenue _venue)
	{
		this.setAddress(_venue.getLocation().getAddress());
		this.setCity(_venue.getLocation().getCity());
		this.setCountry(_venue.getLocation().getCity());
		this.setLatitude(_venue.getLocation().getLat());
		this.setLongitude(_venue.getLocation().getLng());
		this.setPostcode(_venue.getLocation().getPostalCode());
		this.setVenue_name(_venue.getName());
		this.setURL(_venue.getUrl());
	}
	
	public String getCategoryString(Category[] venueCategory) 
	{
		String retString="";
		if(venueCategory == null||venueCategory.length==0)
		{
			
		}
		else {
			for(int i=0;i<venueCategory.length-1;i++)
			{
				retString+=venueCategory[i].getName()+", ";
			}
			retString+=venueCategory[venueCategory.length-1].getName();
		}
		return retString;
	}
	
	public String getVenue_name() {
		return venue_name;
	}
	public void setVenue_name(String venue_name) {
		this.venue_name = venue_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
