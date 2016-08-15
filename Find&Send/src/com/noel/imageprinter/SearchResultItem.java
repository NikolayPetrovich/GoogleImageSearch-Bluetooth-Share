package com.noel.imageprinter;

public class SearchResultItem {
	
	private String mTitle = "";
	private String mUrl = "";
	private String mImageUrl = "";
	private String mContent = "";
	private String mPublished = "";

	public SearchResultItem(String title, String url, String content){
		mTitle = title;
		mUrl = url;
		mContent = content;
	}
	
	public SearchResultItem(String title, String url, String published, String content){
		mTitle = title;
		mImageUrl = url;
		mContent = content;
		mPublished = published;
	}
	
	public SearchResultItem(String title, String url, String imageUrl, String published, String content){
		mTitle = title;
		mUrl = url;
		mContent = content;
		mImageUrl = imageUrl;
		mPublished = published;
	}

	public String GetTitle(){
		if(mTitle.length() > SearchUtils.TEXT_TITLE_MAX_LEN){
			String title = mTitle.substring(0, SearchUtils.TEXT_TITLE_MAX_LEN - 3);
			title += "...";
			return title;
		}
		return mTitle;
	}

	public String GetUrl(){
		return mUrl;
	}

	public String GetImageUrl(){
		return mImageUrl;
	}

	public String GetContent(){
		return mContent;
	}

	public String GetPublished(){
		if(mPublished == null || mPublished.isEmpty())
			return "";
		String time = mPublished;
		if(mPublished.length() > 25)
			time = mPublished.substring(0, 25);
		return time;
	}
}
