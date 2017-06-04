package common;

import java.text.ParseException;
import java.util.ArrayList;

public class ReleaseGrabber {
	
	private static RequestFactorio _requestFactory;
	
	public static void main(String[] args) throws ParseException{
		if(_requestFactory == null) _requestFactory = RequestFactorio.GetInstance();
		ArrayList<Result> results = _requestFactory.GetGamesByPlatform("PS4");
		if(results == null) return;
		results = SortResultsExpectedRelease(results);//Sorted List containing all Games with any future release date
	}
	
	private static ArrayList<Result> SortResultsExpectedRelease(ArrayList<Result> results){
		//find all games with an expected release in the future
		ArrayList<Result> futureGames = new ArrayList<Result>();
		for(Result curRes : results){
			if(curRes.getExpectedReleaseDate() == null) continue;
			futureGames.add(curRes);
		}
		//Sort remaining games by releaseDate
		futureGames.sort((p1, p2) -> p1.getExpectedReleaseDate().compareTo(p2.getExpectedReleaseDate()));
		//Debugging shit--------------------------------------------
		for(Result curRes : futureGames){
			System.out.println(curRes.getName() + " " + curRes.getExpectedReleaseDate().toString());
		}
		System.out.println(futureGames.size());
		//-----------------------------------------------------------
		return futureGames;
	}
}
