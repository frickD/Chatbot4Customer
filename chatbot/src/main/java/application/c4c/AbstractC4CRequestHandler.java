package application.c4c;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class AbstractC4CRequestHandler {
	protected Credentials fCredentials;
	protected String xml;
	protected String urlSnippet;
	
	/**
	 * creates StringXML for request to c4c
	 * @return
	 */
	protected abstract String createXML();
	/**
	 * reads out input in the request specific way
	 * @param in (String from sendRequestToC4C)
	 */
	protected abstract void getResponse(String in);
	
	public AbstractC4CRequestHandler(Credentials credentials){
		fCredentials = credentials;
	}
	/**
	 * Creates connection to C4C.
	 */
	protected HttpURLConnection createConnectionToC4C(URL url, String username, String password) throws IOException{
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        connection.setRequestProperty  ("Authorization", username + "," + password);
        connection.setDoOutput(true);
        return connection;
	}
	
	/**
	 * send request to c4c
	 * @return bufferreader
	 */
	protected String sendRequestToC4C() {
		xml = createXML();
		BufferedReader in = null;
		try {
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fCredentials.getUser(), fCredentials.getPassword().toCharArray());
				}
			});
			URL url = new URL("https://" + fCredentials.getC4C() + this.urlSnippet + fCredentials.getC4C());
			HttpURLConnection connection = this.createConnectionToC4C(url, fCredentials.getUser(), fCredentials.getPassword());
			//DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			//this.writeStringToOutPutStreamInUTF8(xml, wr);
			byte[] postData = xml.getBytes(StandardCharsets.UTF_8);
			try(DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
				wr.write( postData );
				wr.flush();
				wr.close();
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
		}catch(Exception e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "No Response from C4C";
		}
	}
}
