package common;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class RequestFactorio {

	private static final Logger log = Logger.getLogger(RequestFactorio.class.getName());

	private static RequestFactorio _instance = null;
	private static ArrayList<Result> workingSet = new ArrayList<Result>();
	private static boolean allFetched = false;
	protected static final String _apiKey = "1605648d3fb03dafb57cff18686b2af5eec4bf0f";
	protected static final String _baseURL = "https://www.giantbomb.com/api/";

	private RequestFactorio(){

	}

	public static RequestFactorio GetInstance(){
		if(_instance == null){
			_instance = new RequestFactorio();
		}
		return _instance;
	}

	public ArrayList<Result> GetGamesByPlatform(String platform){
		//		String thisMoment = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
		//				.withZone(ZoneOffset.UTC)
		//				.format(Instant.now());
		//
		//		String thisMomentplus = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
		//				.withZone(ZoneOffset.UTC)
		//				.format(Instant.now().plusSeconds(63072000));
		//
		//		String thisMomentminus = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX")
		//				.withZone(ZoneOffset.UTC)
		//				.format(Instant.now().minusSeconds(63072000));
		//
		//		System.out.println(thisMoment);
		//		System.out.println(thisMomentplus);
		//		System.out.println(thisMomentminus);
		String fieldList = "name,platforms,expected_release_day,expected_release_month,expected_release_year,original_release_date,deck,image0";
		//String sort = "release_date:desc&offset=0";
		String filter = "platforms:146";
		int offset = 0;
		//		String urlString = "https://www.giantbomb.com/api/search/?api_key=" + _apiKey + "&format=json&limit=100&query=\"littlebigplanet"
		//				+ "\"&resources=game";
		String queryString = _baseURL + "games/" + "?api_key=" + _apiKey + "&format=json" + "&field_list="+fieldList+"&filter="+filter+"&offset="+offset;//+"&sort="+sort ; 
		/*
		 * 94 	pc
		 * 129	psvita
		 * 138	3ds
		 * 139	wiiu
		 * 146	ps4
		 * 157	switch
		 */
		workingSet = new ArrayList<Result>();
		//System.out.println(queryString);
		try {
			while(allFetched == false){
			URL url = new URL(queryString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
			InputStream response = con.getInputStream();
			Gson gson = new Gson();
			try (Scanner scanner = new Scanner(response)) {
				String responseBody = scanner.useDelimiter("\\A").next();
				//				System.out.println(responseBody);
				JsonResponse jsonResponse = gson.fromJson(responseBody, JsonResponse.class);
				//				System.out.println(jsonResponse);
				jsonResponse.getLimit();
				for(Object s : jsonResponse.getResults()){
					//					System.out.println(s.toString());
					//					System.out.println(gson.toJson(s));
					workingSet.add(gson.fromJson(gson.toJson(s), Result.class)/*megadumm*/);
				}
				System.err.println(offset);
				offset= offset+100;
				if(jsonResponse.getNumberOfTotalResults() == workingSet.size()) allFetched = true;
			}
			}
				return workingSet;

		} catch (Exception e) {
			// TODO logging
			log.warning(e.getMessage());
		}
		return null;
	}
}
