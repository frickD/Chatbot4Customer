package application.c4c;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {
	@JsonProperty("user")
	private final String user;
	@JsonProperty("password")
	private final String password;
	@JsonProperty("c4c")
	private final String c4c;
	@JsonProperty("internalID")
	private final String internalID;
	
	public Credentials() {
		this.user = "";
		this.password = "";
		this.c4c = "";
		this.internalID = "";
	}
	public String getInternalID() {
		return internalID;
	}
	public Credentials(String user, String password, String c4c, String internalID) {
		this.user = user;
		this.password = password;
		this.c4c = c4c;
		this.internalID = internalID;
	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

	public String getC4C() {
		return this.c4c;
	}
}
