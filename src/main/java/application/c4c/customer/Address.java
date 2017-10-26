package application.c4c.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
	@JsonProperty("CorrespondenceLanguageCode")
	private String correspondenceLanguageCode;
	@JsonProperty("FormattedAddress")
	private FormattedAddress formattedAddress;
	@JsonProperty("PostalAddress")
	private PostalAddress postalAddress;
	@JsonProperty("Email")
	private Email email;
	@JsonProperty("WebURI")
	private String webURI;
	
	public Address(){
		correspondenceLanguageCode = "";
		webURI = "";
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public String getCorrespondenceLanguageCode() {
		return correspondenceLanguageCode;
	}

	public FormattedAddress getFormattedAddress() {
		return formattedAddress;
	}

	public void setPostalAddress(PostalAddress postalAddress) {
		this.postalAddress = postalAddress;
	}
	public PostalAddress getPostalAddress() {
		return postalAddress;
	}
	public void setCorrespondenceLanguageCode(String correspondenceLanguageCode) {
		this.correspondenceLanguageCode = correspondenceLanguageCode;
	}

	public void setFormattedAddress(FormattedAddress formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public String getWebURI() {
		return webURI;
	}

	public void setWebURI(String webURI) {
		this.webURI = webURI;
	}
}
