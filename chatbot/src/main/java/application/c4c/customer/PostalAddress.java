package application.c4c.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostalAddress {

	@JsonProperty("CountryCode")
	private String countryCode;
	@JsonProperty("TimeZoneCode")
	private String timeZoneCode;
	@JsonProperty("RegionCode")
	private RegionCode regionCode;
	@JsonProperty("StreetPostalCode")
	private String streetPostalCode;
	@JsonProperty("HouseID")
	private String houseID;
	@JsonProperty("StreetName")
	private String streetName;
	@JsonProperty("CompanyPostalCode")
	private String companyPostalCode;
	
	public PostalAddress(){
		countryCode = "";
		timeZoneCode = "";
		streetPostalCode = "";
		houseID = "";
		streetName = "";
		companyPostalCode = "";
	}

	public String getCompanyPostalCode() {
		return companyPostalCode;
	}

	public void setCompanyPostalCode(String companyPostalCode) {
		this.companyPostalCode = companyPostalCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getTimeZoneCode() {
		return timeZoneCode;
	}

	public RegionCode getRegionCode() {
		return regionCode;
	}

	public String getStreetPostalCode() {
		return streetPostalCode;
	}

	public String getHouseID() {
		return houseID;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}

	public void setRegionCode(RegionCode regionCode) {
		this.regionCode = regionCode;
	}

	public void setStreetPostalCode(String streetPostalCode) {
		this.streetPostalCode = streetPostalCode;
	}

	public void setHouseID(String houseID) {
		this.houseID = houseID;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
}
