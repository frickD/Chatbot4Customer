package application.chatbot;

public class ChatBotSettings {
	private String username;
	private String password;
	private String workspace;
	
	public ChatBotSettings(String username, String password, String workspace) {
		this.username = username;
		this.password = password;
		this.workspace = workspace;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWorkspace() {
		return workspace;
	}
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}
}
