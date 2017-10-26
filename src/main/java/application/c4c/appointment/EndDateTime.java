package application.c4c.appointment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EndDateTime {
	@JsonProperty("TypeCode")
	private String typeCode;
	@JsonProperty("Date")
	private String date;
	@JsonProperty("DateTime")	
	private DateTime dateTime;

	
	
	public EndDateTime(){
		date = "";
		typeCode = "";

	}
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	
}
