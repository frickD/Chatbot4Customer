package application.c4c.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormattedAddress {
	@JsonProperty("FormattedAddressDescription")
	private String formattedAddressDescription;
	@JsonProperty("FormattedPostalAddressDescription")
	private String formattedPostalAddressDescription;
	@JsonProperty("FirstLineDescription")
	private String firstLineDescription;
	@JsonProperty("SecondLineDescription")
	private String secondLineDescription;
	@JsonProperty("ThirdLineDescription")
	private String thirdLineDescription;
	@JsonProperty("FourthLineDescription")
	private String fourthLineDescription;
	@JsonProperty("FormattedAddress")
	private FormattedAddress formattedAddress;
	@JsonProperty("FormattedPostalAddress")
	private FormattedAddress formattedPostalAddress;
	
	public FormattedAddress(){
		formattedAddressDescription = "";
		formattedPostalAddressDescription = "";
		firstLineDescription = "";
		secondLineDescription = "";
		thirdLineDescription = "";
		fourthLineDescription = "";
	}
	
	public FormattedAddress getFormattedPostalAddress() {
		return formattedPostalAddress;
	}

	public void setFormattedPostalAddress(FormattedAddress formattedPostalAddress) {
		this.formattedPostalAddress = formattedPostalAddress;
	}

	public FormattedAddress getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(FormattedAddress formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getFormattedAddressDescription() {
		return formattedAddressDescription;
	}
	public String getFormattedPostalAddressDescription() {
		return formattedPostalAddressDescription;
	}
	public String getFirstLineDescription() {
		return firstLineDescription;
	}
	public String getSecondLineDescription() {
		return secondLineDescription;
	}
	public String getThirdLineDescription() {
		return thirdLineDescription;
	}
	public String getFourthLineDescription() {
		return fourthLineDescription;
	}
	public void setFormattedAddressDescription(String formattedAddressDescription) {
		this.formattedAddressDescription = formattedAddressDescription;
	}
	public void setFormattedPostalAddressDescription(String formattedPostalAddressDescription) {
		this.formattedPostalAddressDescription = formattedPostalAddressDescription;
	}
	public void setFirstLineDescription(String firstLineDescription) {
		this.firstLineDescription = firstLineDescription;
	}
	public void setSecondLineDescription(String secondLineDescription) {
		this.secondLineDescription = secondLineDescription;
	}
	public void setThirdLineDescription(String thirdLineDescription) {
		this.thirdLineDescription = thirdLineDescription;
	}
	public void setFourthLineDescription(String fourthLineDescription) {
		this.fourthLineDescription = fourthLineDescription;
	}
}
