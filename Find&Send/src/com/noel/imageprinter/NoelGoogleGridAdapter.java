package com.noel.imageprinter;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoelGoogleGridAdapter extends BaseAdapter{

	public ArrayList<SearchResultItem> searchResultItems;
	private Context mContext;
	private static LayoutInflater mInflater=null;
    public ImageLoader mImageLoader;
    public int mWidth, mHeight;
	
	public NoelGoogleGridAdapter(Context ctx, ArrayList<SearchResultItem> noelResultItems){
		mContext = ctx;
		searchResultItems = noelResultItems;
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageLoader=new ImageLoader(mContext);
        Resources res = ctx.getResources();
        int displayWidth = res.getDisplayMetrics().widthPixels;
        int space = (int)res.getDimension(R.dimen.grid_spacing);
        mWidth = (displayWidth - space * 6)/3;
        mHeight = mWidth;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return searchResultItems.size();
	}

	@Override
	public SearchResultItem getItem(int position) {
		// TODO Auto-generated method stub
		return searchResultItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        if( convertView==null )
            vi = mInflater.inflate(R.layout.gridview_item, null);
        TextView text=(TextView)vi.findViewById(R.id.textItem);
        ImageView image=(ImageView)vi.findViewById(R.id.imageItem);
        image.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mHeight));
//        text.setSelected(true);
//        text.setText(searchResultItems.get(position).GetTitle());
        text.setVisibility(View.GONE);
        mImageLoader.DisplayImage(searchResultItems.get(position).GetImageUrl(), image, true);
        return vi;
	}

}
