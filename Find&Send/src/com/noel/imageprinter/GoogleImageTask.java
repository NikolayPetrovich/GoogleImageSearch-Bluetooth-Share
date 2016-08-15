package com.noel.imageprinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GoogleImageTask extends AsyncTask<String, Void, String> {

	private static GoogleSearchActivity mContext = null;
	private String TAG = getClass().getName();
	static int mPageNumber;

	public GoogleImageTask(Context context, int pageNumber) {
		super();
		mContext = (GoogleSearchActivity) context;
		mPageNumber = pageNumber;
		mContext.showProgressDialog();;
	}

	@Override
	protected void onPostExecute(String result) {
		JSONObject jsonResult = null;
		try {
			jsonResult = new JSONObject(result);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "===" + e);
			mContext.showToast("Can't get search result.\nCheck your internet connection.");
		}

		try {
			if (jsonResult != null) {
				String searchInformation = jsonResult
						.getString("searchInformation");
				JSONObject obj1 = new JSONObject(searchInformation);

				ArrayList<SearchResultItem> items = new ArrayList<SearchResultItem>();

				if (obj1.getInt("totalResults") > 0) {

					JSONArray value = jsonResult.getJSONArray("items");

					for (int i = 0; i < value.length(); i++) {
						JSONObject jsonValue = new JSONObject(value
								.getJSONObject(i).toString());

						String title = jsonValue.getString("title");
						String image = jsonValue.getString("link");
						String snippet = jsonValue.getString("snippet");

						items.add(new SearchResultItem(title, image, "",
								snippet));
					}
				}
				mContext.searchEnd(items);
//				mContext.ShowResultView(items);

			} else {
				mContext.showToast("Can't get search result.\nCheck your internet connection.");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				String error = jsonResult.getString("error");
				jsonResult = new JSONObject(error);
				String message = jsonResult.getString("message");

				mContext.showToast("Can't get search result.\nCheck your internet connection. Reason : " + message);

			} catch (JSONException e1) {
				mContext.showToast("Can't get search result.\nCheck your internet connection.");
				e1.printStackTrace();
			}

		}
		mContext.dismissProgressDialog();
	}

	@Override
	protected String doInBackground(String... urls) {
		String newUrl = SearchUtils.GetGoogleImageUrl();

		String keyword = urls[0];

		SearchParams searchParams = new SearchParams(keyword);

		return POST(newUrl, searchParams);
	}

	public class SearchParams {

		private String Key;
		private String cx;
		private String query;

		public SearchParams(String query) {
			Key = SearchUtils.GetGoogleKey();
			cx = SearchUtils.GetGoogleCx();
			this.query = query;
		}

		public String GetKey() {
			return Key;
		}

		public String GetCx() {
			return cx;
		}

		public String GetSearchType() {
			return "image";
		}

		public String GetImageSize() {
			return "large";
		}

		public String GetAlt() {
			return "json";
		}

		public String GetQuery() throws UnsupportedEncodingException {
			return URLEncoder.encode(query, "utf8");
		}
	}

	public static String POST(String url, SearchParams searchParams) {
		InputStream inputStream = null;
		String result = "";
		try {
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// searchType=image&imgSize=small&alt=json&q=
			url += "?key=" + searchParams.GetKey();
			url += "&cx=" + searchParams.GetCx();
			url += "&searchType=" + searchParams.GetSearchType();
			url += "&imgSize=" + searchParams.GetImageSize();
			url += "&alt=" + searchParams.GetAlt();
			url += "&q=" + searchParams.GetQuery();
			url += "&num=9";
			if(mPageNumber > 0){
				url += "&start=" + (mPageNumber * 9 + 1);
			}

			HttpGet httpGet = new HttpGet(url);

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpGet);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.e("InputStream", e.getLocalizedMessage());
//			mContext.showToast("Connection to google refused");
		}

		// 11. return result
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}

		inputStream.close();
		return result;

	}
}