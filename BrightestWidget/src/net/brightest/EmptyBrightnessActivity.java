package net.brightest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

public class EmptyBrightnessActivity extends Activity {

	private static final int DELAYED_MESSAGE = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new FinishActivityMessageHandler(this);
    	SharedPreferences pref = getSharedPreferences("Preference", Context.MODE_PRIVATE);
    	int prefBrightness = pref.getInt("brightness", 255);
    	int actualBrightness = getActualBrightness();
    	if ( isMaxBrightness(actualBrightness)  ) {
    		setActualBrightness(prefBrightness);
    	} else {
    		setActualBrightness(255);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("brightness", actualBrightness);
            editor.commit();
    	}
        
        Message message = handler.obtainMessage(DELAYED_MESSAGE);
        //this next line is very important, you need to finish your activity with slight delay
        handler.sendMessageDelayed(message,100); 
    }
	
    private static class FinishActivityMessageHandler extends Handler {
    	private Activity activity;
    	
    	public FinishActivityMessageHandler(Activity activity) {
    		this.activity = activity;
		}
    	
    	@Override
        public void handleMessage(Message msg) {
            if(msg.what == DELAYED_MESSAGE) {
            	this.activity.finish();
            }
            super.handleMessage(msg);
        }
    }
    
    private boolean isMaxBrightness(int brightness) {
    	return brightness == 255;
    }

    private void setActualBrightness(int brightness) {
    	android.provider.Settings.System.putInt(getContentResolver(),android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    	brightness = brightness > 255 ? 255 : brightness;
    	brightness = brightness <= 0 ? 1 : brightness;
    	android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
    	
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = (float) brightness/255;
        getWindow().setAttributes(lp);
    }
    
    private int getActualBrightness() {
    	int result = 255;
    	try {
			result = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (Throwable e) {
			Log.e(BrightestWidget.class.getName(), "getBrightness fail", e);
		}
    	return result;
    }
}
