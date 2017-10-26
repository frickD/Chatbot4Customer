package application.helper;

import java.util.ArrayList;

public abstract class HTMLWrapper {
	
	public static String wrapChatbotResponseToHTML(String responseText){
		return "<p>" + responseText + "</p>";
	}
	
	public static String wrapCustomerListToHTML(String responseText, ArrayList<String> customerList,String city){
		String html = "<p>" + responseText + "</p>";
		if (customerList.size() > 0){
			for (String customer : customerList) {
				html = html + "<p>" + customer + "</p>";
			}
		}
		else{
			return wrapChatbotResponseToHTML("Es befinden sich leider Kunden in " + city + ".");
		}
		return html;
	}
	
	public static String wrapAppointmentListToHTML(String responseText, ArrayList<String> Appointmentlist){
		String html = "<p>" + responseText + "</p>";
		if (Appointmentlist.size() > 0){
			for (String appointment : Appointmentlist) {
				html = html + "<p>" + appointment + "</p>";
			}
		}
		else{
			return wrapChatbotResponseToHTML("Heute hast du keine Termine. Genieße deinen freien Tag!");
		}
		return html;
	}
}
