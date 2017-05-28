package common;

public class ReleaseGrabber {
	
	private static RequestFactory _requestFactory;
	
	public static void main(String[] args){
		if(_requestFactory == null) _requestFactory = RequestFactory.GetInstance();
		_requestFactory.GetGamesByPlatform("PS4");
	}
}
