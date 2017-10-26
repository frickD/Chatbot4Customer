package application.c4c.appointment;


import org.json.JSONObject;
import org.json.XML;

import application.c4c.AbstractC4CRequestHandler;
import application.c4c.Credentials;
/**
 * gets all customer related to the parameter companyName lowBoundaryEmail
 * @param QueryContact containing companyName ..., log in credentials
 *
 */
public class C4CManageAppointmentRequestHandler extends AbstractC4CRequestHandler {
	private JSONObject reJson;
	private boolean isRequestSuccessfull;
	private Credentials credentials;
	private String startTime;
	private String endTime;
	private String name;
	
	public C4CManageAppointmentRequestHandler(Credentials credentials, String name, String startTime, String endTime) {
		super(credentials);
		this.urlSnippet = "/sap/bc/srt/scs/sap/manageappointmentactivityin1?sap-vhost=";
		this.reJson = new JSONObject();
		this.credentials = credentials;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
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
		xml += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\"> <soapenv:Header /> <soapenv:Body> <n0:AppointmentActivityBundleMaintainRequest_sync_V1 xmlns:n0=\"http://sap.com/xi/SAPGlobal20/Global\"><AppointmentActivity actionCode=\"01\"> <ObjectNodeSenderTechnicalID>01</ObjectNodeSenderTechnicalID> <Name>"
				+ name + "</Name> <StartDateTime timeZoneCode=\"CET\">"
				+ startTime + "</StartDateTime> <EndDateTime timeZoneCode=\"CET\">"
				+ endTime + "</EndDateTime> <OrganizerParty> <BusinessPartnerInternalID>"
				+ credentials.getInternalID()+"</BusinessPartnerInternalID> </OrganizerParty> <EmployeeResponsibleParty> <BusinessPartnerInternalID>"
				+ credentials.getInternalID()+"</BusinessPartnerInternalID> </EmployeeResponsibleParty> </AppointmentActivity> </n0:AppointmentActivityBundleMaintainRequest_sync_V1> </soapenv:Body> </soapenv:Envelope>";		
		return xml;
	}

	@Override
	protected void getResponse(String in) {
		String inputLine = in;
		JSONObject xmlJSON = XML.toJSONObject(inputLine);
		JSONObject jTemp = new JSONObject();
		
		try{
			JSONObject jsonObject = xmlJSON.getJSONObject("soap-env:Envelope").getJSONObject("soap-env:Body").getJSONObject("n0:AppointmentActivityBundleMaintainConfirmation_sync_V1");        	
			if (jsonObject.optJSONObject("AppointmentActivity") != null){
				isRequestSuccessfull = true;
				jTemp.put("status", "Response is correct and has created the Appointment");
			}else{
				isRequestSuccessfull = false;
				jTemp.put("status", "Could not create the Appointment");
			}	
		}catch(Exception e){
			jTemp.put("status", in);
			e.printStackTrace();
		}
		this.reJson = jTemp;
	}
	
	public boolean isRequestSuccessfull(){
		return this.isRequestSuccessfull;
	}
	

	public JSONObject getResponseJSON(){
		return reJson;
	}
}
