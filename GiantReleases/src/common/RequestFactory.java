package common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

public class RequestFactory {
	private static final Logger log = Logger.getLogger(RequestFactory.class.getName());

	private static RequestFactory _instance = null;

	protected static final String _apiKey = "1605648d3fb03dafb57cff18686b2af5eec4bf0f";
	protected static final String _baseURL = "https://www.giantbomb.com/api/";

	private RequestFactory(){

	}

	public static RequestFactory GetInstance(){
		if(_instance == null){
			_instance = new RequestFactory();
		}
		return _instance;
	}

	public void GetGamesByPlatform(String platform){
		String fieldList = "name,aliases,description";
		String queryString = _baseURL + "games/" + "?api_key=" + _apiKey + "&format=json&platforms=" + "9" + "&resources=game&field_list="+fieldList ; //9 for pc
		System.out.println(queryString);
		try {
			URL url = new URL(queryString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
			InputStream response = con.getInputStream();
			try (Scanner scanner = new Scanner(response)) {
				String responseBody = scanner.useDelimiter("\\A").next();
				System.out.println(responseBody);
			}
			//			int response = con.getResponseCode();
			//			System.out.println(response);

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
