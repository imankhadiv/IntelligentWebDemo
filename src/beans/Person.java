package beans;

import java.util.Map;

public class Person {
	private int id;
	private String name;
	private String screenName;
	private String location;
	private String profilePicture;
	private String description;
	private String twittText;
	private int twittCount;
	
	private Map<String, Integer> map;

	public String getTwittText() {
		return twittText;
	}

	public void setTwittText(String twittText) {
		this.twittText = twittText;
	}

	public int getTwittCount() {
		return twittCount;
	}

	public void setTwittCount(int twittCount) {
		this.twittCount = twittCount;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String id) {
		this.screenName = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
