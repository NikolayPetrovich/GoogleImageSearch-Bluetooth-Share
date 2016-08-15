package com.noel.imageprinter;

import java.io.File;
import java.io.FilenameFilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class ImageViewActivity extends Activity implements SearchView.OnQueryTextListener{

	private GridView mImageGrid;
	private SearchView mSearchView;
	private String noelFolderName = "NoelPhoto";
	private String noelPath;
	private String imagePaths[];
	private NoelGridAdapter adapter;
	private FilenameFilter mFileNameFilter;
	private File mNoelFolder;
	private String searchText="";
	private Button mGoogleButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageview);
		mGoogleButton = (Button)findViewById(R.id.googleBtn);
		mGoogleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent googleIntent = new Intent(ImageViewActivity.this, GoogleSearchActivity.class);
				googleIntent.putExtra("searchKey", searchText);
				startActivity(googleIntent);
			}
		});
		mImageGrid = (GridView)findViewById(R.id.imageGrid);
		mImageGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				// TODO Auto-generated method stub
				String selectedPath = (String)mImageGrid.getItemAtPosition(position);
				Intent printIntent = new Intent(ImageViewActivity.this, PrintViewActivity.class);
				printIntent.putExtra("Path", selectedPath);
				startActivity(printIntent);
			}
		});
		noelPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + noelFolderName;
		mNoelFolder = new File(noelPath);
		if (!mNoelFolder.exists()) mNoelFolder.mkdir();
		searchText = "";
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshGrid(searchText.toLowerCase());
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_google){
			Intent googleIntent = new Intent(ImageViewActivity.this, GoogleSearchActivity.class);
			googleIntent.putExtra("searchKey", searchText);
			startActivity(googleIntent);
		}
		return super.onOptionsItemSelected(item);
	}



	public void initSearchView(){
		LinearLayout linearLayout1 = (LinearLayout) mSearchView.getChildAt(0);
		LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
		LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
		AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
		autoComplete.setTextSize(27);
		mSearchView.setQueryHint("Search Here");
		mSearchView.setOnQueryTextListener(this);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		searchText = newText;
		refreshGrid(newText.toLowerCase());
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		Log.e("Nikolay", " Query SUbmit search Text = " + query);
		return false;
	}
	
	public void refreshGrid(final String refreshKey){
		mFileNameFilter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				
				String tmpfilename = filename;
				filename = tmpfilename.toLowerCase();
				return filename.contains(refreshKey) && (filename.contains(".jpeg") || filename.contains(".jpg"));
			}
		};
		imagePaths = mNoelFolder.list(mFileNameFilter);
		int len = imagePaths.length;
		for ( int i = 0; i < len; i++ ){
			imagePaths[i] = noelPath + File.separator + imagePaths[i];
		}
		adapter=new NoelGridAdapter(this, imagePaths);
        mImageGrid.setAdapter(adapter);
        
        mGoogleButton.setVisibility(mImageGrid.getCount()==0?View.VISIBLE:View.GONE);
        
	}
}
