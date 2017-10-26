package application.c4c.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionCode {
	@JsonProperty("listID")
	private String listID;
	@JsonProperty("content")
	private String content;
	
	public RegionCode(){
		listID = "";
		content = "";
	}

	public String getListID() {
		return listID;
	}

	public String getContent() {
		return content;
	}

	public void setListID(String listID) {
		this.listID = listID;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
