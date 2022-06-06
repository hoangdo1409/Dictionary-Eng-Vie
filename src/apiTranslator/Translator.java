package apiTranslator;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;


 
public class Translator {
	private final static String urlStr = "https://script.google.com/macros/s/AKfycbyyKjByiI3ti-cPhcyxCmFEs_-GDkvbd-M_V0t1tnb0qcD4-A3M8Lqvebe6bcrm3Vt5/exec";
	
	public static String translate(String text, Languages langto, Languages langfrom) throws MalformedURLException, UnsupportedEncodingException {
		APIConnector api = new APIConnector(urlStr);
		String query = getQuery(text, langto, langfrom);
		return (String) api.getJSONObject(query).get("text");
	}
	
	public static String getQuery(String text, Languages langto, Languages langfrom ) throws UnsupportedEncodingException {
		String query  = "?q=" + URLEncoder.encode(text, "UTF-8")
	            + "&target=" + langto.getCode()
	            + "&source=" + langfrom.getCode();
		return query;
	}
}