package googlemap;

import fi.foyt.foursquare.api.entities.CompactUser;

public class User_4s {
	private String photoURL;
	private String firstName;
	private String lastName;
	private String gender;
	private String homeCity;
	private String id;
	
	public User_4s(CompactUser _user) {
		this.setId(_user.getId().toString());
		this.setFirstName(_user.getFirstName());
		this.setLastName(_user.getLastName());
		this.setPhotoURL(_user.getPhoto());
		this.setGender(_user.getGender());
		this.setHomeCity(_user.getHomeCity());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHomeCity() {
		return homeCity;
	}
	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}
	
}
