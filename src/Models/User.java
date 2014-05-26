package Models;

public class User {
	
	public User(String name,String id,String location,String description,String photoURL,String userId)
	{
		this.setName(name);
		this.setId(id);
		this.setLocation(location);
		this.setPhotoURL(photoURL);
		this.setDescription(description);
		this.setId(userId);
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
	

}
