package com.photofilter;


import java.util.ArrayList;
import java.util.Collections;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class FilterMain extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_SAVE_SETTINGS = 2;
	private Bitmap filteredImage;
	private int maskSize;
	private String filterType;
	private ProgressDialog pd;
	private Context context;
	private ImageView imageView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_filter_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId()) {
    	case R.id.action_choose_pic:
    		// We're going to get the picture here.
    		getPicture();
    		return true;
    	case R.id.action_settings:
    		// We're going to open the filter settings here.
    		openSettings();
    		return true;
    	case R.id.action_do_filter:
    		// We're going to apply the filter according to the settings.
    		applyFilter();
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    /*
     *  This method calls an external image app (Gallery, or Photo Gallery) to
     *  retrieve an image through the intent.
     */
    public void getPicture()
    {
    	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    
    /*
     * This method is called when we return from the getPicture() action.
     * We shall obtain the image, and then show it in our ImageView.
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
    
    /*
     * Start the Settings activity.
     */
    public void openSettings()
    {
    	Intent settingsIntent = new Intent(this, FilterSettingsActivity.class);
    	startActivityForResult(settingsIntent, RESULT_SAVE_SETTINGS);
    }
    
    /*
     * This happens when the button to apply the filter is clicked.
     */
    public void applyFilter()
    {	
    	// Save the preferences to the local variables
    	SharedPreferences filterPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	filterType = filterPreferences.getString("prefFilterType", "");
    	maskSize = Integer.parseInt(filterPreferences.getString("prefFilterSize", ""));
    	
    	ImageView imageView = (ImageView) findViewById(R.id.image);
    	
    	// Let's check if there is a picture selected first.
    	if(imageView.getDrawable() == null)
    	{
    		Context context = getApplicationContext();
    		CharSequence text = "You need to pick a photo first!";
    		int duration = Toast.LENGTH_SHORT;

    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}
    	else
    	{
    		// Perform the filtering!
    		Bitmap inputImage = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    		(new FilterTask()).execute(inputImage);
    		Log.d("Filter", "Starting filtering with type " + filterType + " and mask size of " + maskSize);
    	}
    }

    /*
     * Subclass for the filter process.
     */
    private class FilterTask extends AsyncTask<Bitmap, Integer, Void> {

    	@Override
    	protected void onPreExecute()
    	{
    		if(pd == null)
    		{
    			pd = new ProgressDialog(context);
    		}
    		pd.setTitle("Applying Filter...");
    		pd.setCancelable(false);
    		pd.setMax(100);
    		pd.setIndeterminate(false);
    		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		pd.show();
    	}
    	
    	@Override
    	protected Void doInBackground(Bitmap... inputs) {
    		Log.d("AsyncTask", "Beginning Filtering");
    		Log.d("AsyncTask", "Mask Type:" + filterType);
    		Log.d("AsyncTask", "Mask Size:" + maskSize);
    		Bitmap inputImage = inputs[0];
    		Bitmap returnImage = inputImage.copy(Bitmap.Config.ARGB_8888, true);
    		int height = inputImage.getHeight();
    		int width = inputImage.getWidth();

    		// We create an ArrayList to temporarily store the pixels selected by mask size
    		ArrayList<Integer> tempArray = new ArrayList<Integer>();
    		int radius = maskSize / 2;
    		int count = 0;
    		
    		for(int i = 0; i < width; i++)
    		{
    			for(int j = 0; j < height; j++)
    			{
    				// Valid pixels should be equal to
    				// [midpoint - radius, midpoint + radius]
    				// (Midpoint being the currently selected pixel)
    				int lowWidthBound = i - radius;
    				int highWidthBound = i + radius;
    				int lowHeightBound = j - radius;
    				int highHeightBound = j + radius;
    				for (int x = lowWidthBound; x <= highWidthBound; x++)
    				{
    					for(int y = lowHeightBound; y <= highHeightBound; y++)
    					{
    						// Add the pixels to our temporary array list
    						// Only if they are valid!!
    						if(x >= 0 && x < width)
    						{
    							if(y >= 0 && y < height)
    							{
    								tempArray.add(inputImage.getPixel(x, y));
    							}
    						}
    					}
    				}
    				
    				// Build the new color here.
    				int red = 0;
					int blue = 0;
					int green = 0;
					int size = tempArray.size();
    				if(filterType.equals("Mean"))
    				{
    					for(int color:tempArray)
    					{
    						red+=(Color.red(color) / size);
    						blue+=(Color.blue(color) / size);
    						green+=(Color.green(color) / size);
    					}
    				}
    				
    				if(filterType.equals("Median"))
    				{
    					ArrayList<Integer> redColors = new ArrayList<Integer>();
    					ArrayList<Integer> blueColors = new ArrayList<Integer>();
    					ArrayList<Integer> greenColors = new ArrayList<Integer>();
    					for(int color:tempArray)
    					{
    						redColors.add(Color.red(color));
    						blueColors.add(Color.blue(color));
    						greenColors.add(Color.green(color));
    					}

    					Collections.sort(redColors);
    					Collections.sort(blueColors);
    					Collections.sort(greenColors);
    					red = redColors.get(redColors.size()/2);
    					blue = blueColors.get(blueColors.size()/2);
    					green = greenColors.get(greenColors.size()/2);

    					redColors.clear();
    					blueColors.clear();
    					greenColors.clear();
    				}
    				returnImage.setPixel(i, j, Color.rgb(red, green, blue));
    				count++;
    				publishProgress((count*100) / (width * height));
    				// Clear the temporary array list
    				tempArray.clear();
    			}
    		}
    		filteredImage = returnImage;
    		return null;
    	}
    	
    	@Override
    	public void onProgressUpdate(Integer... args)
    	{
    		pd.setProgress(args[0]);
    	}
    		
    	@Override
    	protected void onPostExecute(Void result)
    	{
    		imageView.setImageBitmap(filteredImage);
    		if(pd != null)
    		{
    			pd.dismiss();
    		}
    	}
    }
    
}
