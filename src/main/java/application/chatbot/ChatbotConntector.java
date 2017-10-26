package application.chatbot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.RuntimeEntity;

import application.c4c.C4CCons;
import application.c4c.Credentials;
import application.c4c.appointment.C4CAppointmentRequestHandler;
import application.c4c.appointment.C4CManageAppointmentRequestHandler;
import application.c4c.customer.C4CCustomerRequestHandler;
import application.helper.HTMLWrapper;
import application.helper.Utils;

public class ChatbotConntector {
	private String inputString;
	private MessageResponse response;
	private String returnText;
	private Credentials credentials;
	private ChatBotSettings settings;
	private ChatBotIntents intent;
	
	public ChatbotConntector(String inputString, Credentials credentials, ChatBotSettings settings) throws ParseException {
		this.inputString = inputString;
		this.credentials = credentials;
		this.settings = settings;
		connectToChatbot();
	}
	
	private void connectToChatbot() throws ParseException{
		Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
		service.setUsernameAndPassword(settings.getUsername(), settings.getPassword());
		InputData input = new InputData.Builder(inputString).build();
		MessageOptions options = new MessageOptions.Builder(settings.getWorkspace()).input(input).build();
		MessageResponse response = service.message(options).execute();
		this.response = response;
		try{
			returnText = HTMLWrapper.wrapChatbotResponseToHTML(response.getOutput().getText().get(0));
		}catch(Exception e){
			returnText = "";
		}
		checkForC4CActions();
	}
	
	private void checkForC4CActions() throws ParseException{
		this.intent = getIntent();
		if (checkForCustomerSearch()){
			String city = getCityOfCustomerSearch();
			C4CCustomerRequestHandler c4cHandler = new C4CCustomerRequestHandler(this.credentials, city);
			returnText = HTMLWrapper.wrapCustomerListToHTML(returnText, c4cHandler.getCustomerList(), city);
		}else if (this.intent == ChatBotIntents.appointmentsToday){
			C4CAppointmentRequestHandler c4cHandler = new C4CAppointmentRequestHandler(this.credentials, Utils.getStartOfDay(C4CCons.C4CTIMEFORMAT), Utils.getEndOfDay(C4CCons.C4CTIMEFORMAT));
			returnText = HTMLWrapper.wrapAppointmentListToHTML(returnText, c4cHandler.getAppointmentsOfTodayList());
		}else if(this.intent == ChatBotIntents.appointmentTimeleft){
			C4CAppointmentRequestHandler c4cHandler = new C4CAppointmentRequestHandler(this.credentials, Utils.getCurrentTimeAsString(C4CCons.C4CTIMEFORMAT), Utils.getEndOfDay(C4CCons.C4CTIMEFORMAT));
			returnText = returnText + HTMLWrapper.wrapChatbotResponseToHTML(c4cHandler.getTimeUntilNextAppointment());
		}else if(this.intent == ChatBotIntents.appointmentNext){
			C4CAppointmentRequestHandler c4cHandler = new C4CAppointmentRequestHandler(this.credentials, Utils.getCurrentTimeAsString(C4CCons.C4CTIMEFORMAT), Utils.getEndOfDay(C4CCons.C4CTIMEFORMAT));
			returnText = returnText + HTMLWrapper.wrapChatbotResponseToHTML(c4cHandler.getNextAppointment());
		}else if(this.intent == ChatBotIntents.appoinmentNextLocation){
			C4CAppointmentRequestHandler c4cHandler = new C4CAppointmentRequestHandler(this.credentials, Utils.getCurrentTimeAsString(C4CCons.C4CTIMEFORMAT), Utils.getEndOfDay(C4CCons.C4CTIMEFORMAT));
			returnText = returnText + HTMLWrapper.wrapChatbotResponseToHTML(c4cHandler.getLocationOfNextAppointment());
		}else if(this.intent == ChatBotIntents.appointmentCreateToday && checkForAppointmentCreation()){
			HashMap<String, String> entities = getEntites();
			String startTime = Utils.convertSysTimeToToday(entities.get("sys-time").toString(), C4CCons.C4CTIMEFORMAT);
			String endTime = Utils.addMinutesToTimeString(startTime, C4CCons.C4CTIMEFORMAT,30);
			String name = getNameOfAppointment(response.getInput().getText());
			C4CManageAppointmentRequestHandler c4cHandler = new C4CManageAppointmentRequestHandler(credentials, name, startTime, endTime);
			if (c4cHandler.isRequestSuccessfull()){
				returnText = returnText.replace("$title$", name);
			}else{
				returnText = HTMLWrapper.wrapChatbotResponseToHTML("Konnte in deinem Kalender keinen Eintrag erstellen.");
			}
			
		}
	}
	
	private String getNameOfAppointment(String input){
		Pattern p = Pattern.compile("(b|B)etreff.*");
		Matcher m = p.matcher(input);

		List<String> name = new ArrayList<String>();
		while (m.find()) {
			System.out.println("Found name of appointment: " + m.group() + ".");
			name.add(m.group());
		}
		
		return name.get(0).substring(8);
	}
	
	private String getCityOfCustomerSearch(){
		return response.getContext().get("city").toString();
	}
	
	private HashMap<String, String> getEntites(){
		HashMap<String, String> entities = new HashMap<>();
		for (RuntimeEntity iterable_element : response.getEntities()) {
			entities.put(iterable_element.getEntity(), iterable_element.getValue());
		}
		return entities;
	}
	
	private boolean checkForCustomerSearch(){
		Boolean isCustomerSearch;
		try {
			isCustomerSearch = Boolean.valueOf(response.getContext().get("isCompleteForSearch").toString());
		}catch (Exception e){
			isCustomerSearch = false;
		}
		return isCustomerSearch;
	}
	
	private boolean checkForAppointmentCreation(){
		Boolean isComplete;
		try {
			isComplete = Boolean.valueOf(response.getContext().get("isCompleteForCreateAppointment").toString());
		}catch (Exception e){
			isComplete = false;
		}
		return isComplete;
	}
	
	private ChatBotIntents getIntent(){
		String intentString =  response.getIntents().get(0).getIntent().toString();
		if(intentString.matches(ChatBotIntents.appointmentsToday.getString()) ){
			return ChatBotIntents.appointmentsToday;
		}else if (intentString.matches(ChatBotIntents.appointmentTimeleft.getString())){
			return ChatBotIntents.appointmentTimeleft;
		}else if (intentString.matches(ChatBotIntents.appointmentNext.getString())){
				return ChatBotIntents.appointmentNext;
		}else if (intentString.matches(ChatBotIntents.appoinmentNextLocation.getString())){
					return ChatBotIntents.appoinmentNextLocation;		
		}else if (intentString.matches(ChatBotIntents.searchCustomer.getString())){
			return ChatBotIntents.searchCustomer;
		}else if (intentString.matches(ChatBotIntents.appointmentCreateToday.getString())){
			return ChatBotIntents.appointmentCreateToday;
		}else{
			return ChatBotIntents.defaultIntent;
		}
	}
	
	public String getOutput(){
		return returnText;
	}
	
}


