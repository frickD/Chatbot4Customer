package application.c4c.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationName {
	@JsonProperty("LocationName")
	private String locationName;
	
	public LocationName() {
		// TODO Auto-generated constructor stub
	}
	
	public LocationName(String locationName){
		this.locationName = locationName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
