package application.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import application.c4c.Credentials;
import application.chatbot.ChatBotSettings;

public class SettingsReader {
	
	private final static String APPLICATIONPROPERTIES = "application.properties";
	private static Properties prop;
	
	static{
		prop = new Properties();
		InputStream input = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			input = classLoader.getResourceAsStream(APPLICATIONPROPERTIES);
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Credentials getCredentials(){
		return new Credentials(prop.getProperty("user"), prop.getProperty("password"), prop.getProperty("c4c"), prop.getProperty("internalid"));
	}
	
	public static ChatBotSettings getChatBotSettings(){
		return new ChatBotSettings(prop.getProperty("BOTusername"), prop.getProperty("BOTpassword"), prop.getProperty("BOTworkspace"));
	}
}
