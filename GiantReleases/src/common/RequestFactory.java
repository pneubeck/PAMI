package common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RequestFactory {
	
	private static RequestFactory _instance = null;
	
	protected static final String _apiKey = "1605648d3fb03dafb57cff18686b2af5eec4bf0f";
	protected static final String _baseURL = "http://www.giantbomb.com/api/";
	
	private RequestFactory(){
		
	}
	
	public static RequestFactory GetInstance(){
		if(_instance == null){
			_instance = new RequestFactory();
		}
		return _instance;
	}
	
	public void GetGamesByPlatform(String platform){
		String queryString = _baseURL + "games/" + "?api_key=" + _apiKey + "&format=json&platforms=" + "9" + "&resources=game"; //9 for pc
		try {
			URL url = new URL(queryString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			String response = con.getResponseMessage();
			System.out.println(response);
		} catch (MalformedURLException e) {
			// TODO logging
		} catch (ProtocolException e) {
			// TODO logging
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO logging
		}
	}
}
