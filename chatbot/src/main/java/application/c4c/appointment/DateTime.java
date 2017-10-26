package application.c4c.appointment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTime {
	@JsonProperty("timeZoneCode")
	private String timeZoneCode;
	@JsonProperty("content")
	private String content;

	
	
	public DateTime(){
		timeZoneCode = "";
		content = "";

	}



	public String getTimeZoneCode() {
		return timeZoneCode;
	}



	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}
	
}
