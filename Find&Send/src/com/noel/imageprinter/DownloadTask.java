package com.noel.imageprinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class DownloadTask extends AsyncTask<String, Void, String>{
	
	public GoogleSearchActivity mContext;
	public File outFile;
	
	public DownloadTask(Context ctx, File file){
		mContext = (GoogleSearchActivity)ctx;
		mContext.showDownloadingProgress();
		outFile = file;
	}
	
	

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		if( !outFile.exists() )
			try {
				outFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		super.onPreExecute();
	}



	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			URL imageUrl = new URL(params[0]);
	        HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
	        conn.setConnectTimeout(30000);
	        conn.setReadTimeout(30000);
	        conn.setInstanceFollowRedirects(true);
	        InputStream is=conn.getInputStream();
	        OutputStream os = new FileOutputStream(outFile);
	        Utils.CopyStream(is, os);
	        os.close();
		}catch(Exception ex){
			return null;
		}
		return "success";
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		mContext.dismissDownloadingProgress();
		if (result==null){
			mContext.showToast(R.string.download_failed);
			mContext.processFailed(outFile);
		}else{
			mContext.showToast(R.string.download_success);
			mContext.startPrintPreview();
		}
		super.onPostExecute(result);
	}
	
	
}
