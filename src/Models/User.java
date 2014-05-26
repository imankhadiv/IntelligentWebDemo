package Models;

public class User {
	
	public User(String name,String userId,String location,String description,String photoURL,String id)
	{
		this.setName(name);
		this.setId(id);
		this.setLocation(location);
		this.setPhotoURL(photoURL);
		this.setDescription(description);
		this.setScreenName(userId);
	}
	
	
	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	private String name;
	private String id;
	private String location;
	private String description;
	private String photoURL;
	private String screenName;
	

}
