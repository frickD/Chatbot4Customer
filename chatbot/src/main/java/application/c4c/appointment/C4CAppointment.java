package application.c4c.appointment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class C4CAppointment {
	@JsonProperty("Name")
	private String name;
	@JsonProperty("EndDateTime")
	private EndDateTime endDateTime;
	@JsonProperty("StartDateTime")	
	private StartDateTime startDateTime;
	@JsonProperty("LocationName")	
	private LocationName locationName;

	
	
	public LocationName getLocationName() {
		return locationName;
	}

	public void setLocationName(LocationName locationName) {
		this.locationName = locationName;
	}

	public C4CAppointment(){
		name = "";

	}
	
	public String getName() {
		return name;
	}
	public EndDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setEndDateTime(EndDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public StartDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(StartDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	
}
