package com.noel.imageprinter;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoelGridAdapter extends BaseAdapter{

	private String mImagePaths[];
	private Context mContext;
	private static LayoutInflater mInflater=null;
    public ImageLoader mImageLoader;
    public int mWidth, mHeight;
	
	public NoelGridAdapter(Context ctx, String[] noelImages){
		mContext = ctx;
		mImagePaths = noelImages;
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
		return mImagePaths.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return mImagePaths[position];
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
        int nameStIndex = mImagePaths[position].lastIndexOf(File.separatorChar);
        text.setSelected(true);
        text.setText(mImagePaths[position].substring(nameStIndex+1));
        mImageLoader.DisplayImage(mImagePaths[position], image, false);
        return vi;
	}

}
