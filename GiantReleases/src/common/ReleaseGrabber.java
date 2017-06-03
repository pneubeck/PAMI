package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReleaseGrabber {
	
	private static RequestFactorio _requestFactory;
	
	public static void main(String[] args) throws ParseException{
		if(_requestFactory == null) _requestFactory = RequestFactorio.GetInstance();
		ArrayList<Result> results = _requestFactory.GetGamesByPlatform("PS4");
		if(results == null) return;
		SortResultsExpectedRelease(results);
	}
	
	private static ArrayList<Result> SortResultsExpectedRelease(ArrayList<Result> results){
		//find all games with an expected release in the future
		ArrayList<Result> futureGames = new ArrayList<Result>();
		for(Result curRes : results){
			if(curRes.getExpectedReleaseDate() == null) continue;
			futureGames.add(curRes);
		}
		//Sort remaining games by releaseDate
		//futureGames.sort((p1, p2) -> p1.getExpectedReleaseDate().compareTo(p2.getExpectedReleaseDate()));
		for(Result curRes : futureGames){
			System.out.println(curRes.getName());
		}
		System.out.println(futureGames.size());
		return futureGames;
	}
}
