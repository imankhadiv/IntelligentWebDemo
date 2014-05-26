package Models;

import java.util.Date;
import java.util.List;

public class TweetWithURL {
	private String idString;
	private String text;
	private List<String> displayURL;
	private List<String> expandedURL;
	private String createdAt;
	
	public TweetWithURL(String _text,List<String> _displayURL, List<String> _expandedURL, String _createdAt,String id)
	{
		this.idString = id;
		this.text = _text;
		this.displayURL = _displayURL;
		this.expandedURL = _expandedURL;
		this.createdAt = _createdAt;
	}
	
	public String getIdString() {
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getDisplayURL() {
		return displayURL;
	}

	public void setDisplayURL(List<String> displayURL) {
		this.displayURL = displayURL;
	}

	public List<String> getExpandedURL() {
		return expandedURL;
	}

	public void setExpandedURL(List<String> expandedURL) {
		this.expandedURL = expandedURL;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


}
