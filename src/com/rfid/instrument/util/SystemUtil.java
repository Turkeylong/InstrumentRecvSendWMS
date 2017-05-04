package com.rfid.instrument.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

public class SystemUtil {

    public static void startAlarm(Context context) 
    {  
    	MediaPlayer mp = new MediaPlayer();// MediaPlayer.create(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    	try 
    	{
	    	mp.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
	    	mp.prepare();
	    	mp.start();
    	} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	} 
    }
}
