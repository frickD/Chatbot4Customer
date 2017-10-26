package application.c4c.appointment;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import application.c4c.AbstractC4CRequestHandler;
import application.c4c.C4CCons;
import application.c4c.Credentials;
import application.helper.Utils;
/**
 * gets all customer related to the parameter companyName lowBoundaryEmail
 * @param QueryContact containing companyName ..., log in credentials
 *
 */
public class C4CAppointmentRequestHandler extends AbstractC4CRequestHandler {
	private JSONObject reJson;
	private String startTime;
	private String endTime;
	private ArrayList<C4CAppointment> appointmentList;
	
	public C4CAppointmentRequestHandler(Credentials credentials, String startTime, String endTime) {
		super(credentials);
		this.urlSnippet = "/sap/bc/srt/scs/sap/queryappointmentactivityin?sap-vhost=";
		this.reJson = new JSONObject();
		this.endTime = endTime;
		this.startTime = startTime;
		this.appointmentList = new ArrayList<>();
		
		try {
			String in = this.sendRequestToC4C();

			getResponse(in);
	        
			
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String createXML() {
		String xml = "";
		xml += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\"> <soapenv:Header /> <soapenv:Body> <n0:AppointmentActivityByElementsQuery_Sync_V1 xmlns:n0=\"http://sap.com/xi/SAPGlobal20/Global\"> <AppointmentActivitySelectionByElements> <SelectionByBusinessPartnerInternalID> <!--Optional:--> <InclusionExclusionCode>I</InclusionExclusionCode> <!--Optional:--> <IntervalBoundaryTypeCode>1</IntervalBoundaryTypeCode> <!--Optional:--> <LowerBoundaryInternalID>"
				+ this.fCredentials.getInternalID()
				+ "</LowerBoundaryInternalID> <!--Optional:--> <UpperBoundaryInternalID></UpperBoundaryInternalID> </SelectionByBusinessPartnerInternalID> <SelectionByReportedDateTime> <!--Optional:--> <InclusionExclusionCode>I</InclusionExclusionCode> <IntervalBoundaryTypeCode>3</IntervalBoundaryTypeCode> <!--Optional:--> "
				+ "<LowerBoundaryDateTime timeZoneCode=\"CET\">"
				+ this.startTime
				+ "</LowerBoundaryDateTime> <!--Optional:--> "
				+ "<UpperBoundaryDateTime timeZoneCode=\"CET\">"
				+ this.endTime
				+ "</UpperBoundaryDateTime> </SelectionByReportedDateTime> </AppointmentActivitySelectionByElements> <ProcessingConditions> <QueryHitsMaximumNumberValue>25</QueryHitsMaximumNumberValue> <QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator> </ProcessingConditions></n0:AppointmentActivityByElementsQuery_Sync_V1></soapenv:Body> </soapenv:Envelope>";		
		return xml;
	}

	@Override
	protected void getResponse(String in) {
		String inputLine = in;
		JSONObject xmlJSON = XML.toJSONObject(inputLine);
		JSONObject jTemp = new JSONObject();
		
		try{
			JSONObject jsonObject = xmlJSON.getJSONObject("soap-env:Envelope").getJSONObject("soap-env:Body").getJSONObject("n0:AppointmentActivityByElementsResponse_sync_V1");        	
			JSONArray jArray = new JSONArray();
			
			ObjectMapper m = new ObjectMapper();
			m.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			if(jsonObject.optJSONArray("AppointmentActivity") != null){
				C4CAppointment[] cus = m.readValue(jsonObject.getJSONArray("AppointmentActivity").toString(), C4CAppointment[].class);
				m = new ObjectMapper();
				m.configure(SerializationFeature.INDENT_OUTPUT, true);
				m.setSerializationInclusion(Include.NON_NULL);
				m.setSerializationInclusion(Include.NON_EMPTY);
				for(C4CAppointment cusT : cus){
					this.appointmentList.add(cusT);
					jArray.put(new JSONObject(m.writerWithDefaultPrettyPrinter().writeValueAsString(cusT)));
				}
			}
			else if (jsonObject.optJSONObject("AppointmentActivity") != null){
				C4CAppointment cus = m.readValue(jsonObject.getJSONObject("AppointmentActivity").toString(), C4CAppointment.class);
				m = new ObjectMapper();
				m.configure(SerializationFeature.INDENT_OUTPUT, true);
				m.setSerializationInclusion(Include.NON_NULL);
				m.setSerializationInclusion(Include.NON_EMPTY);
				jArray.put(new JSONObject(m.writerWithDefaultPrettyPrinter().writeValueAsString(cus)));
				this.appointmentList.add(cus);
			}
			
			if(jArray.length() != 0){
				jTemp.put("customers", jArray);
				jTemp.put("status", "Response is correct and has some Appointments as return");
			}else{
				jTemp.put("status", "No Appointments today.");
			}	
		}catch(Exception e){
			jTemp.put("status", in);
			e.printStackTrace();
		}
		sortAppointmentListByDate();
		this.reJson = jTemp;
	}
	
	public ArrayList<String> getAppointmentsOfTodayList() throws ParseException{
		ArrayList<String> appointments = new ArrayList<>();
		for (C4CAppointment c4cAppointment : appointmentList) {
			appointments.add( formatToDayTime(Utils.changeFromGMTToGivenTimeZone(c4cAppointment.getStartDateTime().getDateTime().getContent(), c4cAppointment.getStartDateTime().getDateTime().getTimeZoneCode())) +" - " + formatToDayTime(Utils.changeFromGMTToGivenTimeZone(c4cAppointment.getEndDateTime().getDateTime().getContent(), c4cAppointment.getEndDateTime().getDateTime().getTimeZoneCode())) 
			+ " : " + c4cAppointment.getName());
		}
		return appointments;
	}
	
	public String getNextAppointment() throws ParseException{
		if (appointmentList.size() > 0){
			String name = appointmentList.get(0).getName();
			Date appointment = Utils.formatStringToDate(C4CCons.C4CTIMEFORMAT, appointmentList.get(0).getStartDateTime().getDateTime().getContent());
			Calendar cal = Calendar.getInstance(); 
		    cal.setTime(appointment);
		    cal.add(Calendar.HOUR_OF_DAY, 2);
			return "Dein nächster Termin '" + name + "' beginnt um " + (new SimpleDateFormat("HH:mm")).format(new Date(cal.getTimeInMillis())) + ".";
			
		}
		else{
			return "Für heute stehen in deinem Kalender keine Einträge mehr. Feierabend!";
		}
	}
	
	
	public String getTimeUntilNextAppointment() throws ParseException{
		if (appointmentList.size() > 0){
			String name = appointmentList.get(0).getName();
			Date appointment = Utils.formatStringToDate(C4CCons.C4CTIMEFORMAT, appointmentList.get(0).getStartDateTime().getDateTime().getContent());
			Calendar cal = Calendar.getInstance(); 
		    cal.setTime(appointment);
		    cal.add(Calendar.HOUR_OF_DAY, 2);
			long timeleft = Utils.calculateMillisBetweenTwoDates(cal.getTime(), Utils.getCurrentTime());
			long minutes =  timeleft / (60 * 1000) % 60; 
			long hours = TimeUnit.MILLISECONDS.toHours(timeleft);
			
			if (hours > 0){
				return "Bis zu deinem nächsten Termin '"+ name +"' hast du noch " + hours + " Stunden und " + minutes + " Minuten."; 
			}else {
				return "In " + minutes + " steht dein nächster Termin an";
			}
		}
		else{
			return "Heute hast du keine weiteren Kalendareinträge. Feierabend!";
		}
		
	}
	
	private String formatToDayTime(String time){
		try {
			return Utils.formatDateToNewFormat(C4CCons.C4CTIMEFORMAT, "HH:mm", time, C4CCons.C4CTIMEZONEID);
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
	}
	
	private void sortAppointmentListByDate(){
		Collections.sort(appointmentList, new Comparator<C4CAppointment>() {
		    @Override
		    public int compare(C4CAppointment lhs, C4CAppointment rhs) {
		    	Date lhsDate = null;
				try {
					lhsDate = Utils.formatStringToDate(C4CCons.C4CTIMEFORMAT, lhs.getStartDateTime().getDateTime().getContent());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	Date rhsDate = null;
				try {
					rhsDate = Utils.formatStringToDate(C4CCons.C4CTIMEFORMAT, rhs.getStartDateTime().getDateTime().getContent());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        if (lhsDate.getTime() < rhsDate.getTime())
		            return -1;
		        else if (lhsDate.getTime() == rhsDate.getTime())
		            return 0;
		        else
		            return 1;
		    }
		});
	}
	

	public JSONObject getResponseJSON(){
		return reJson;
	}

	public String getLocationOfNextAppointment() {
		if (appointmentList.size() > 0){
			String name = appointmentList.get(0).getName();
			String location = appointmentList.get(0).getLocationName().getLocationName();
			if (!location.matches("")){
				return "Der Ort für deinen nächsten Termin '" + name + "' lautet: " + location + ".";
			}else{
				return "Dein nächster Termin '"+name+"' hat leider keine Ortsangabe in der C4C";
			}	
		}
		else{
			return "Heute musst du nicht mehr weg, es gibt keine Kalendereinträge mehr. Feierabend!";
		}
	}
}
