package com.noel.imageprinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PrintViewActivity extends Activity {

	private String mPath;
	private ImageView mPrintView;
	private ImageButton mPrintButton;
	private int mWidth, mHeight;
	private File printFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printview);
		mPrintView = (ImageView)findViewById(R.id.printview);
		mPrintButton = (ImageButton)findViewById(R.id.printButton);
		mPrintButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startPrint();
			}
		});
		mPath = getIntent().getStringExtra("Path");
		mWidth = 626;
		mHeight = 939;
		initPrintView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void initPrintView(){
		String printPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "print_image.jpg";
		printFile = new File(printPath);
		Bitmap image = decodeFile(new File(mPath));
		if (image.getWidth() > image.getHeight()) {
			Matrix matrix = new Matrix();
		    matrix.postRotate(90);
		    image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
		}
		File printFile = new File(printPath);
//		String printLayerPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "photopaper.png";
//		Bitmap printLayer = BitmapFactory.decodeFile(printLayerPath);
		Bitmap printLayer = BitmapFactory.decodeResource(getResources(), R.drawable.photopaper);
		if (printLayer != null && image != null ){
			printLayer = printLayer.copy(Config.ARGB_8888, true);
			printLayer = Bitmap.createBitmap(printLayer);
			Canvas canvas = new Canvas(printLayer);
		    float scaleWidth = mWidth / (float)image.getWidth();
		    float scaleHeight = mHeight / (float)image.getHeight();
		    if (scaleWidth < scaleHeight) scaleHeight = scaleWidth;
		    else scaleWidth = scaleHeight;
		    // create a matrix for the manipulation
		    Matrix matrix = new Matrix();
		    // resize the bit map
		    matrix.postScale(scaleWidth, scaleHeight);
		    float dx = (mWidth - image.getWidth() * scaleWidth) / 2.0f;
		    float dy = (mHeight - image.getHeight() * scaleHeight) / 2.0f;
		    matrix.postTranslate(dx, dy);
		    // recreate the new Bitmap
			canvas.drawBitmap(image, matrix, null);
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(printFile);
				int quality = 100;
				printLayer.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
		        outputStream.flush();
		        outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mPrintView.setImageBitmap(printLayer);
	}
	
	public void startPrint(){
		if (!printFile.exists()){
			Log.e("Nikolay", "No such File");
			return;
		}
		Uri uri = Uri.fromFile(printFile);
		Intent sharingIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/jpeg");
        sharingIntent
                .setComponent(new ComponentName(
                        "com.android.bluetooth",
                        "com.android.bluetooth.opp.BluetoothOppLauncherActivity"));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(sharingIntent);
	}
	
	private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
             
            //Find the correct scale value. It should be the power of 2.
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2< mWidth && height_tmp/2 < mHeight)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
             
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
}
