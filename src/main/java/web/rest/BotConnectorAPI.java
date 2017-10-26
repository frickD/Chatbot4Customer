package web.rest;

import java.text.ParseException;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import application.chatbot.ChatbotConntector;
import application.helper.SettingsReader;
import web.Input;

@ApplicationPath("api")
@Path("/input")
public class BotConnectorAPI extends Application{

	@POST
	@Produces("application/text")
    @Consumes("application/json")
    public String connectToBot(Input input) throws ParseException {
		ChatbotConntector conntector = new ChatbotConntector(input.getInput(), SettingsReader.getCredentials(), SettingsReader.getChatBotSettings());
		return conntector.getOutput();
    }
}
