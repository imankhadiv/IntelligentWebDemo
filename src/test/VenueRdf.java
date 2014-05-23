package test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class VenueRdf {

	Model model;
	Property propVenueName;
	Property propLongitude;
	Property propLatitude;


	public void venueRdf(){
		
		String venueURI = "http://somewhere/venue";
		model = ModelFactory.createDefaultModel();
		
		//create schema
		
		propVenueName = model.createProperty(venueURI, "venueName");
		propLongitude = model.createProperty(venueURI, "longitude");
		propLatitude = model.createProperty(venueURI, "latitude");	 
	}
	
	public void saveVenue(String venueName, String longitude, String Latitude){
	
		//create resource and add property
		Resource newVenue = model.createResource().addProperty(propVenueName, venueName).addProperty(propLongitude, longitude)
		.addProperty(propLatitude, Latitude);
	}

	
	public Model getModel(){
		return model;
	}
}

