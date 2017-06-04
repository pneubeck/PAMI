package common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

public class ReleaseGrabber {
	
	private static RequestFactorio _requestFactory;
	
	public static void main(String[] args) throws ParseException{
		if(_requestFactory == null) _requestFactory = RequestFactorio.GetInstance();
		ArrayList<Result> results = _requestFactory.GetGamesByPlatform("PS4");
		if(results == null) return;
		results = SortResultsExpectedRelease(results);//Sorted List containing all Games with any future release date
		try {
			CreateIcal(results);
		} catch (ValidationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	//Creates and returns an Ical from the given resultList
	private static void CreateIcal(ArrayList<Result> results) throws ValidationException, IOException{
		//Creating calendar
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//PAMI//iCal4j 2.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		int uId = 1;
		//Creatng events for all games
		for(Result curRes : results){
			//Create calendar from game release date
			Date date;
			GregorianCalendar myCal = new GregorianCalendar();
			myCal.setTime(curRes.getExpectedReleaseDate());
			//Creating ICAL Event
			java.util.Calendar calEvent = java.util.Calendar.getInstance();
			calEvent.set(java.util.Calendar.YEAR, myCal.YEAR);
			calEvent.set(java.util.Calendar.MONTH, myCal.MONTH);
			calEvent.set(java.util.Calendar.DAY_OF_MONTH, myCal.DAY_OF_MONTH);
			// initialise as an all-day event..
			VEvent game = new VEvent(new Date(calEvent.getTime()), curRes.getName());
			// Generate a UID for the event..
			UidGenerator ug = new UidGenerator(String.valueOf(uId));
			uId = uId + 1; 
			game.getProperties().add(ug.generateUid());
			//Adding the Event to the calendar
			calendar.getComponents().add(game);
		}
		//Writing the Ical to disc
		FileOutputStream fout = new FileOutputStream("mycalendar.ics");

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(calendar, fout);
	}
}
