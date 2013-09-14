package com.emanga;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emanga.views.Thumbnail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ThumbnailAdapter extends BaseAdapter {
	public static String TAG = ThumbnailAdapter.class.getName();
	
	private Context mContext;

	public List<Thumbnail> thumbnails = new ArrayList<Thumbnail>();
	
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	
    public ThumbnailAdapter(Context c) {
        mContext = c;
        
    	options = new DisplayImageOptions.Builder()
	    	.showImageForEmptyUri(R.drawable.ic_content_picture)
	    	.showImageOnFail(R.drawable.ic_content_remove)
	    	.cacheInMemory(true)
	    	.cacheOnDisc(true)
	    	.bitmapConfig(Bitmap.Config.RGB_565)
	    	.build();
    	imageLoader = ImageLoader.getInstance();
    }

    public int getCount() {
        return thumbnails.size();
    }

    public Thumbnail getItem(int position) {
        return thumbnails.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new TextView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LinearLayout layout;
    	TextView date;
    	final ProgressBar spinner;
    	final ImageView cover;
    	TextView title;
    	TextView chapter;
    	
        // if it's not recycled, initialize some attributes
    	if (convertView == null) {  
    		/* --------------------- */
    		/* | Date  	           | */
    		/* | Cover <-> Spinner | */
    		/* | Title 			   | */
    		/* | Number			   | */
    		/* --------------------- */
    		layout = new LinearLayout(mContext);
    		layout.setOrientation(LinearLayout.VERTICAL);
    		layout.setLayoutParams(new GridView.LayoutParams(
    			     (int) mContext.getResources().getDimension(R.dimen.gridview_thumb_width),
    			     (int) mContext.getResources().getDimension(R.dimen.gridview_thumb_height)
    			     ));
    		
    		layout.setBackgroundColor(Color.WHITE);
    		
    		date = new TextView(mContext);
    		// Shows while image is loading then spinner will be replace by the image
    		spinner = new ProgressBar(mContext, null, android.R.attr.progressBarStyleSmall);
    		cover = new ImageView(mContext);
    		title = new TextView(mContext);
    		chapter = new TextView(mContext);
    		
    		LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
    				LayoutParams.MATCH_PARENT,
    				LayoutParams.WRAP_CONTENT
    				);
    		
    		paramsText.setMargins(8, 8, 8, 8);
    		
    		date.setLayoutParams(paramsText);
    		title.setLayoutParams(paramsText);
    		
    		date.setMaxLines(1);
    		title.setMaxLines(1);
    		chapter.setMaxLines(1);
    		
    		spinner.setVisibility(View.VISIBLE);
    		
    		cover.setLayoutParams(new LinearLayout.LayoutParams(
    				(int) mContext.getResources().getDimension(R.dimen.gridview_thumb_width),
    				(int) mContext.getResources().getDimension(R.dimen.gridview_cover_height)
    				));
    		
    		cover.setVisibility(View.GONE);
    		
    		date.setGravity(Gravity.CENTER);
    		title.setGravity(Gravity.CENTER);
    		chapter.setGravity(Gravity.CENTER);
    		
    		date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
    		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    		
    		layout.addView(date);
    		layout.addView(spinner);
    		layout.addView(cover);
    		layout.addView(title);
    		layout.addView(chapter);
    		
    	} else {
    		layout = (LinearLayout) convertView;
    		
    		date = (TextView) layout.getChildAt(0);
    		spinner = (ProgressBar) layout.getChildAt(1);
    		cover = (ImageView) layout.getChildAt(2);
    		cover.setVisibility(View.GONE);
    		
    		title = (TextView) layout.getChildAt(3);
    		chapter = (TextView) layout.getChildAt(4);
    	}
    	
    	Thumbnail thumb = getItem(position);
    	
    	date.setText(thumb.date);
    	
    	imageLoader.displayImage(getItem(position).image, cover, options, new SimpleImageLoadingListener() {
    		@Override
    		public void onLoadingStarted(String imageUri, View view) {
    			spinner.setVisibility(View.VISIBLE);
    			cover.setVisibility(View.GONE);
    		}

    		@Override
    		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
    			String message = null;
    			switch (failReason.getType()) {
		    		case IO_ERROR:
		    			message = "Input/Output error";
		    		break;
		    		case DECODING_ERROR:
		    			message = "Image can't be decoded";
		    		break;
		    		case NETWORK_DENIED:
		    			message = "Downloads are denied";
		    		break;
		    		case OUT_OF_MEMORY:
		    			message = "Out Of Memory error";
		    		break;
		    		case UNKNOWN:
		    			message = "Unknown error";
		    		break;
    		}
    			Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

    			spinner.setVisibility(View.GONE);
    		}

    		@Override
    		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
    			spinner.setVisibility(View.GONE);
    			cover.setVisibility(View.VISIBLE);
    		}
    	});
    	
    	title.setText(thumb.title);
    	chapter.setText(thumb.number);
    	
        return layout;
    }
    
}
