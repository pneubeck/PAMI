package common;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

	private RequestFactorio() {

	}

	public static RequestFactorio GetInstance() {
		if (_instance == null) {
			_instance = new RequestFactorio();
		}
		return _instance;
	}

	
	public ArrayList<Result> GetGamesByPlatform(String platform) {
		/*
		 * 94 pc 129 psvita 138 3ds 139 wiiu 146 ps4 157 switch
		 */

		String fieldList = "name,platforms,expected_release_day,expected_release_month,expected_release_year,original_release_date,deck,image0";
		String filter = "platforms:146";
		int offset = 0;
		workingSet = new ArrayList<Result>();
		try {
			while (allFetched == false) {
				String queryString = _baseURL + "games/" + "?api_key=" + _apiKey + "&format=json" + "&field_list="
						+ fieldList + "&filter=" + filter + "&offset=" + offset;// +"&sort="+sort
																				// ;
				URL url = new URL(queryString);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
				InputStream response = con.getInputStream();
				Gson gson = new Gson();
				try (Scanner scanner = new Scanner(response)) {
					String responseBody = scanner.useDelimiter("\\A").next();
					JsonResponse jsonResponse = gson.fromJson(responseBody, JsonResponse.class);
					jsonResponse.getLimit();
					for (Object s : jsonResponse.getResults()) {
						workingSet.add(gson.fromJson(gson.toJson(s),
								Result.class)/*
												 * TODO
												 * megadumm---------------------
												 * ------------
												 */);
					}
					System.err.println(offset);
					offset = offset + 100;
					if (jsonResponse.getNumberOfTotalResults() <= workingSet.size())
						allFetched = true;
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
