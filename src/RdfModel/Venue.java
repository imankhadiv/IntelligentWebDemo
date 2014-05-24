package RdfModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Venue extends BaseModel{

	private final String URI = "http://somewhere";
	private Model model;
	private Property venueName;
	private Property address;
	private Property postcode;
	private Property country;
	private Property city;
	private Property latitude;
	private Property longitude;
	private Property photoURL;
	private Property fromTweet;

	public Venue() {
		String venueURI = URI + "/venue#";
		model = ModelFactory.createDefaultModel();
		venueName = model.createProperty(venueURI, "venueName");
		address = model.createProperty(venueURI, "address");
		postcode = model.createProperty(venueURI, "postcode");
		country = model.createProperty(venueURI, "country");
		city = model.createProperty(venueURI, "city");
		latitude = model.createProperty(venueURI, "latitude");
		longitude = model.createProperty(venueURI, "longitude");
		photoURL = model.createProperty(venueURI, "photoURL");
		fromTweet = model.createProperty(venueURI, "fromTweet");
	}

	public void saveVenue(String venueNameStr, String addressStr,
			String postcodeStr, String countryStr, String cityStr,
			String latStr, String longStr, String photoURLStr, String tweetIdStr) {
		String venueURI = URI + "/venue#" + venueNameStr + latStr + longStr;

		String tweetURI = URI + "/tweet#" + tweetIdStr;
		Resource tweet = model.createResource(tweetURI);
		model.createResource(venueURI).addProperty(venueName, venueNameStr)
				.addProperty(address, addressStr)
				.addProperty(postcode, postcodeStr)
				.addProperty(country, countryStr).addProperty(city, cityStr)
				.addProperty(latitude, latStr).addProperty(longitude, longStr)
				.addProperty(photoURL, photoURLStr)
				.addProperty(fromTweet, tweetIdStr);

	}

	public Model getModel() {
		return model;
	}
}
