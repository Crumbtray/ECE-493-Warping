package com.clintonwong.renderscripttest;

import android.os.Bundle;
import android.renderscript.*;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView mNormalImage, mBlurImage;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mNormalImage = (ImageView) findViewById(R.id.image_normal);
        mBlurImage = (ImageView) findViewById(R.id.image_blurred);
        
        //Start with an image from our APK resources
        Bitmap inBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        Bitmap outBitmap = inBitmap.copy(inBitmap.getConfig(), true);
       
        //Create the context and I/O allocations
        final RenderScript rs = RenderScript.create(this);
        final Allocation input = Allocation.createFromBitmap(rs, inBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        
        // Transform the image
        final ScriptC_transform script = new ScriptC_transform(rs, getResources(), R.raw.transform);
        script.set_width(inBitmap.getWidth());
        script.set_height(inBitmap.getHeight());
        script.bind_input(input);
        script.bind_output(output);
        script.invoke_swirl(50);
        output.copyTo(outBitmap);
        
        // Show the results
        mNormalImage.setImageBitmap(inBitmap);
        mBlurImage.setImageBitmap(outBitmap);
        rs.destroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
