package application.c4c.customer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import application.c4c.AbstractC4CRequestHandler;
import application.c4c.Credentials;
/**
 * gets all customer related to the parameter companyName lowBoundaryEmail
 * @param QueryContact containing companyName ..., log in credentials
 *
 */
public class C4CCustomerRequestHandler extends AbstractC4CRequestHandler {
	JSONObject reJson;
	String cityName;
	ArrayList<String> customerList;
	
	public C4CCustomerRequestHandler(Credentials credentials, String cityName) {
		super(credentials);
		this.urlSnippet = "/sap/bc/srt/scs/sap/querycustomerin1?sap-vhost=";
		reJson = new JSONObject();
		this.cityName = cityName;
		this.customerList = new ArrayList<>();
		
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
		xml += "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\"> <soapenv:Header /> <soapenv:Body> <n0:CustomerByCommunicationDataQuery_sync xmlns:n0=\"http://sap.com/xi/SAPGlobal20/Global\"> <CustomerSelectionByCommunicationData> <SelectionBySearchText>"
				+ this.cityName
				+ "</SelectionBySearchText> <SelectionByPostalAddressCountryCode>DE</SelectionByPostalAddressCountryCode> </CustomerSelectionByCommunicationData> <ProcessingConditions> <QueryHitsMaximumNumberValue>10</QueryHitsMaximumNumberValue> <QueryHitsUnlimitedIndicator>false</QueryHitsUnlimitedIndicator> </ProcessingConditions> </n0:CustomerByCommunicationDataQuery_sync> </soapenv:Body> </soapenv:Envelope>";		
		return xml;
	}

	@Override
	protected void getResponse(String in) {
		String inputLine = in;
		JSONObject xmlJSON = XML.toJSONObject(inputLine);
		JSONObject jTemp = new JSONObject();
		
		try{
			JSONObject jsonObject = xmlJSON.getJSONObject("soap-env:Envelope").getJSONObject("soap-env:Body").getJSONObject("n0:CustomerByCommunicationDataResponse_sync");        	
			JSONArray jArray = new JSONArray();
			
			ObjectMapper m = new ObjectMapper();
			m.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			if(jsonObject.optJSONArray("Customer") != null){
				C4CCustomer[] cus = m.readValue(jsonObject.getJSONArray("Customer").toString(), C4CCustomer[].class);
				m = new ObjectMapper();
				m.configure(SerializationFeature.INDENT_OUTPUT, true);
				m.setSerializationInclusion(Include.NON_NULL);
				m.setSerializationInclusion(Include.NON_EMPTY);
				for(C4CCustomer cusT : cus){
					this.customerList.add(cusT.getAddressInformation().getAddress().getFormattedAddress().getFormattedAddressDescription());
					jArray.put(new JSONObject(m.writerWithDefaultPrettyPrinter().writeValueAsString(cusT)));
				}
			}
			else if (jsonObject.optJSONObject("Customer") != null){
				C4CCustomer cus = m.readValue(jsonObject.getJSONObject("Customer").toString(), C4CCustomer.class);
				m = new ObjectMapper();
				m.configure(SerializationFeature.INDENT_OUTPUT, true);
				m.setSerializationInclusion(Include.NON_NULL);
				m.setSerializationInclusion(Include.NON_EMPTY);
				this.customerList.add(cus.getAddressInformation().getAddress().getFormattedAddress().getFormattedAddressDescription());
				jArray.put(new JSONObject(m.writerWithDefaultPrettyPrinter().writeValueAsString(cus)));
			}
			
			if(jArray.length() != 0){
				jTemp.put("customers", jArray);
				jTemp.put("status", "Response is correct and has some Customers as return");
			}else{
				jTemp.put("status", "Warning! No Customers found");
			}	
		}catch(Exception e){
			jTemp.put("status", in);
			e.printStackTrace();
		}
		this.reJson = jTemp;
	}
	
	public ArrayList<String> getCustomerList(){
		return this.customerList;
	}

	public JSONObject getResponseJSON(){
		return reJson;
	}
}
