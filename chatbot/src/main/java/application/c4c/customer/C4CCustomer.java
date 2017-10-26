package application.c4c.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class C4CCustomer {
	@JsonProperty("UUID")
	private String uuid;
	@JsonProperty("InternalID")
	private String internalID;
	@JsonProperty("ChangeStateID")
	private String changeStateID;
	@JsonProperty("AddressInformation")	
	private AddressInformation[] addressInformation;

	
	
	public C4CCustomer(){
		uuid = "";
		internalID = "";
		changeStateID = "";

	}
	
	public String getUuid() {
		return uuid;
	}
	public String getInternalID() {
		return internalID;
	}
	public String getChangeStateID() {
		return changeStateID;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setInternalID(String internalID) {
		this.internalID = internalID;
	}
	public void setChangeStateID(String changeStateID) {
		this.changeStateID = changeStateID;
	}

	public AddressInformation getAddressInformation() {
		return addressInformation[0];
	}

	public void setAddressInformation(AddressInformation[] addressInformation) {
		this.addressInformation = addressInformation;
	}
	
}
