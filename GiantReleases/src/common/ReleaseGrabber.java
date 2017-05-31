package common;

public class ReleaseGrabber {
	
	private static RequestFactorio _requestFactory;
	
	public static void main(String[] args){
		if(_requestFactory == null) _requestFactory = RequestFactorio.GetInstance();
		_requestFactory.GetGamesByPlatform("PS4");
	}
}
