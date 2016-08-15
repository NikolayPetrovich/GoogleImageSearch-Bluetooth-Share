package com.noel.imageprinter;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

public class GoogleSearchActivity extends Activity implements SearchView.OnQueryTextListener{

	private SearchView mSearchView;
	private String searchText;
	private static ProgressDialog mProgressDialog = null;
	private static ProgressDialog mDownloadDialog = null;
	private MyGridView mGoogleImageGrid;
	private int pageNumber;
	private ArrayList<SearchResultItem> totalSearchResultItems;
	private Button mShowMoreButton;
	private static String printPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "print_image.jpg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google);
		searchText = getIntent().getStringExtra("searchKey");
		mProgressDialog = new ProgressDialog(this);
		mDownloadDialog = new ProgressDialog(this);
		mGoogleImageGrid = (MyGridView)findViewById(R.id.googleGrid);
		mGoogleImageGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				// TODO Auto-generated method stub
				SearchResultItem item = (SearchResultItem)mGoogleImageGrid.getItemAtPosition(position);
				startLoading(item.GetImageUrl());
			}
		});
//		mGoogleImageGrid.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
//				// TODO Auto-generated method stub
//				showDownloadDialog((SearchResultItem)mGoogleImageGrid.getItemAtPosition(position));
//				return false;
//			}
//			
//		});
		mShowMoreButton = (Button)findViewById(R.id.showMore);
		mShowMoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pageNumber++;
				new GoogleImageTask(GoogleSearchActivity.this, pageNumber).execute(searchText);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_main, menu);
	    mSearchView = (SearchView)menu.findItem(R.id.search).getActionView();
	    initSearchView();
	    return true;
	}

	public void initSearchView(){
		LinearLayout linearLayout1 = (LinearLayout) mSearchView.getChildAt(0);
		LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
		LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
		AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
		autoComplete.setTextSize(27);
		mSearchView.setQueryHint("Search Here");
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setQuery(searchText, true);
		mSearchView.setIconified(false);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		searchText = query;
		pageNumber = 0;
		totalSearchResultItems = new ArrayList<SearchResultItem>();
		new GoogleImageTask(GoogleSearchActivity.this, pageNumber).execute(query);
		return false;
	}
	
	public void searchEnd(ArrayList<SearchResultItem> searchResultItems){
		Log.e("Nikolay", "receive end message");
		if (searchResultItems == null) return;
		int size = searchResultItems.size();
		Log.e("Nikolay", "size = " + size);
		for ( int i = 0; i < size; i++){
			totalSearchResultItems.add(searchResultItems.get(i));
		}
		mGoogleImageGrid.setAdapter(new NoelGoogleGridAdapter(this, totalSearchResultItems));
		if (size == 9){
			mShowMoreButton.setVisibility(View.VISIBLE);
		}else{
			mShowMoreButton.setVisibility(View.GONE);
		}
	}
	
	public void showProgressDialog(){
		SpannableString spMessage=  new SpannableString(getResources().getString(R.string.search_dlg_text));
		spMessage.setSpan(new RelativeSizeSpan(1.7f), 0, spMessage.length(), 0);  
		mProgressDialog = ProgressDialog.show(this, "", spMessage);
	}
	
	public void dismissProgressDialog(){
		if (mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	public void showDownloadingProgress(){
		SpannableString spMessage=  new SpannableString(getResources().getString(R.string.downloading));
		spMessage.setSpan(new RelativeSizeSpan(1.7f), 0, spMessage.length(), 0);
		mDownloadDialog = ProgressDialog.show(this, "", spMessage);
	}
	
	public void dismissDownloadingProgress(){
		if (mDownloadDialog != null){
			mDownloadDialog.dismiss();
			mDownloadDialog = null;
		}
	}
	
//	public void showDownloadDialog(final SearchResultItem resultItem){
//		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View contentView = inflater.inflate(R.layout.dialog_layout, null);
//		
//		
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//				this);
//
//		// set prompts.xml to alertdialog builder
//		alertDialogBuilder.setView(contentView);
//
//		final EditText userInput = (EditText) contentView
//				.findViewById(R.id.fileName);
//		userInput.setText(searchText);
//		userInput.setSelection(searchText.length());
//
//		// set dialog message
//		alertDialogBuilder
//			.setCancelable(true)
//			.setPositiveButton(getResources().getString(R.string.downlaod_btn_ok),
//			  new DialogInterface.OnClickListener() {
//			    public void onClick(DialogInterface dialog,int id) {
//			    	
//			    	//startDownload(resultItem.GetImageUrl(), userInput.getText().toString());
//			    }
//			  })
//			.setNegativeButton(getResources().getString(R.string.download_btn_cancel),
//			  new DialogInterface.OnClickListener() {
//			    public void onClick(DialogInterface dialog,int id) {
//			    	dialog.cancel();
//			    }
//			  });
//
//		// create alert dialog
//		final AlertDialog alertDialog = alertDialogBuilder.create();
//
//		// show it
//		alertDialog.show();
//		
//		alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
//	      {            
//	          @Override
//	          public void onClick(View v)
//	          {
//	              String tmpPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "NoelPhoto" + File.separator + userInput.getText().toString() + ".jpg";
//	              File tmpFile = new File(tmpPath);
//	              if (tmpFile.exists()){
//	            	  Toast.makeText(GoogleSearchActivity.this, R.string.name_err, Toast.LENGTH_SHORT).show();
//	            	  return;
//	              }
//	              startDownload(resultItem.GetImageUrl(), tmpFile);
//            	  alertDialog.dismiss();
//	              //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
//	          } 
//	      });
//	}
	
	public void showToast(int messageID){
		Toast.makeText(this, getResources().getString(messageID), Toast.LENGTH_SHORT).show();
	}
	
	public void showToast(String message){
		dismissProgressDialog();
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
//	public void startDownload(String url, File outFile){
//		new DownloadTask(this, outFile).execute(url);
//	}
	
	public void startLoading(String url){
		
		File outFile = new File(printPath);
		new DownloadTask(this, outFile).execute(url);
	}
	
	public void processFailed(File outFile){
		if (outFile.exists()) outFile.delete();
	}
	
	public void startPrintPreview(){
		Intent printIntent = new Intent(GoogleSearchActivity.this, PrintViewActivity.class);
		printIntent.putExtra("Path", printPath);
		startActivity(printIntent);
	}
	
}
