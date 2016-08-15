package com.noel.imageprinter;

public class SearchUtils {
	private static boolean IsBusy = false;
	private static boolean Debug_Wihout_Network = false;

	public static final int TEXT_TITLE_MAX_LEN = 33;
	public static final int TEXT_LINK_MAX_LEN = 45;
	public static final int TEXT_LINK_MAX_LEN2 = 40;
	
	public static final int MSG_SEARCHEND = 0x0001;

	public static void SetSystemBusy(boolean isBusy) {
		IsBusy = isBusy;
	}

	public static boolean IsBusy() {
		return IsBusy;
	}

	// ============================== Google ===================================

	// ------------ Google Key ---------------------
	public static String GetGoogleKey() {
		return "AIzaSyDv7jpkY-IO2kYtmpR8zIDlObjp_aJhq60";
	}

	public static String GetGooglePlaceKey() {
		return "AIzaSyDtS3-quL3Dv_R9FkiIXt1xxwxKaIwXXkc";
		//return "AIzaSyDACDybZJQwISMurwswPaZzI9pAXLRsCzo";
	}

	public static String GetGoogleCx() {
		return "002879008029960316334:sid5ssbeifc";
	}

	// ------------ Google Search URL ---------------------
	public static String GetGoogleSearchUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/text.php";
		return "https://www.googleapis.com/customsearch/v1";
	}

	public static String GetGoogleImageUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/image.php";
		return "https://www.googleapis.com/customsearch/v1";
	}

	public static String GetGoogleVideoUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/video.php";
		return "https://ajax.googleapis.com/ajax/services/search/video";
	}

	public static String GetGoogleNewsUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/news.php";
		return "https://ajax.googleapis.com/ajax/services/search/news";
	}
	
	// ================================== Yahoo ==============================

	// key and secret
	public static String GetYahooKey() {
		return "dj0yJmk9QWRKN1FLQ0tHTDBoJmQ9WVdrOVVWbEVURUpZTmpRbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1iYg--";
	}

	public static String GetYahooSecret() {
		return "639ab8726b5f0acd4a74bb88ffb22249e7fd6c01";
	}

	// Get URLs
	public static String GetYahooWebUrl() {
		return "https://yboss.yahooapis.com/ysearch/web";
	}

	public static String GetYahooImageUrl() {
		return "https://yboss.yahooapis.com/ysearch/images";
	}

	public static String GetYahooVideoUrl() {
		return "https://yboss.yahooapis.com/ysearch/video";
	}

	public static String GetYahooNewsUrl() {
		return "https://yboss.yahooapis.com/ysearch/news";
	}

	// ================================== Bing =================================

	public static String GetBingKey() {
		return "vb91nSpU96SIV06NL1eKW9yEFo9pIyPyCsKRh0Boy7s";
	}

	// https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Market=%27en-GB%27&$format=json&Query=%27ronaldo%27&$top=10
	public static String GetBingWebUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/text_bing.php";
		return "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web";
	}
	
	//https://api.datamarket.azure.com/Bing/Search/Image?$format=json&Query=%27ronaldo%27&Market=%27en-us%27&$top=10
	public static String GetBingImageUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/image_bing.php";
		return "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Image";
	}
	
	//https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Video?Market=%27en-GB%27&$format=json&Query=%27ronaldo%27&$top=10
	public static String GetBingVideoUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/video_bing.php";
		return "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Video";
	}

	//https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/News?Market=%27en-GB%27&$format=json&Query=%27ronaldo%27&$top=10
	public static String GetBingNewsUrl() {
		if (Debug_Wihout_Network)
			return "http://192.168.1.132/searchimage/tmpimg/news_bing.php";
		return "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/News";
	}
}
